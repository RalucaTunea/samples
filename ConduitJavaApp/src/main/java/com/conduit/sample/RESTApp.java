package com.conduit.sample;

import com.conduit.sample.api.ApiClient;
import com.conduit.sample.config.AppConfig;
import com.conduit.sample.services.ConduitAuthenticationService;
import com.conduit.sample.services.ConduitConnectorService;
import com.conduit.sample.services.ConduitDBRequestService;
import com.conduit.sample.services.ConduitRESTQueryService;
import com.conduit.sample.services.entity.ExploreRequest;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class RESTApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCApp.class);

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();

        // Authentication/Login
        ApiClient apiClient = ApiClient.getInstance();
        ConduitAuthenticationService conduitAuthenticationService = new ConduitAuthenticationService(apiClient);
        if (!appConfig.getTokenUser().equals(""))
        //AD authentication
        {
            apiClient.setToken(appConfig.getTokenUser());
        } else
        // basic + anonymous authentication
        {
            conduitAuthenticationService.authentication(appConfig.getEmail(), appConfig.getPassword());
        }

        //Create connector
        ConduitConnectorService<String, List<ExploreRequest>, List<String>> createConnectorService = new ConduitConnectorService(apiClient);
        if (!createConnectorService.verifyIsUniqueConnectorName(appConfig.getConnectorName())) {
            LOGGER.warn("Connector already exist.");
            System.exit(1);
        }
        ConduitDBRequestService<List<ExploreRequest>> dbRequestService = new ConduitDBRequestService(apiClient);

        dbRequestService.setDbRequest(appConfig.getAuthenticationType(), appConfig.getPassword(), appConfig.getConnectionUrl(), appConfig.getConnectionUsername(), appConfig.getDescription(), appConfig.getConnectorName(), appConfig.getNamespace(), appConfig.getSubscriptionId());
        List<ExploreRequest> dbResponse = dbRequestService.getResponseFromDB(dbRequestService.getDbRequest());
        createConnectorService.setCreateConnectorRequest(dbResponse, dbRequestService.getDbRequest(), appConfig.getTypeName(), appConfig.getTables(), appConfig.getAuthenticationType(), appConfig.getSpecificColumns(), appConfig.getAuthorizationEnabled(), appConfig.getUserSubscription());
        String resultOfCreateConnector = createConnectorService.createConnector().toString();
        LOGGER.info("Create Connector Response: {}", resultOfCreateConnector);

        //Verify if connector was successfully created
        assertTrue(resultOfCreateConnector.contains("HTTP/1.1 200 OK"));

        //REST query
        ConduitRESTQueryService restQueryService = new ConduitRESTQueryService(apiClient);
        restQueryService.verifyExistingConnectors(appConfig.getConnectorName());

        String sql = "SELECT * FROM " + appConfig.getTypeName() + "_" + appConfig.getConnectorName() + "." + appConfig.getTables().get(0) + " limit 10";
        LOGGER.info("SQL RESPONSE {}", restQueryService.executeQuery(sql));
        LOGGER.info("QUERY - STATUS {}", restQueryService.getExecuteQueryResponse().getStatus());

        while (restQueryService.getExecuteQueryResponse().getStatus().equals("Running")) {
            restQueryService.getResult(restQueryService.getExecuteQueryResponse().getIdQuery());
            LOGGER.info("QUERY - STATUS {}", restQueryService.getExecuteQueryResponse().getStatus());
        }
    }
}




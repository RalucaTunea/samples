package com.conduit.sample;

import com.conduit.sample.api.ApiClient;
import com.conduit.sample.config.AppConfig;
import com.conduit.sample.services.ConduitAuthenticationService;
import com.conduit.sample.services.ConduitConnectorService;
import com.conduit.sample.services.ConduitDBRequestService;
import com.conduit.sample.services.ConduitRESTQueryService;
import com.conduit.sample.services.entity.ExploreRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class RESTApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCApp.class);

    public static void main(String[] args) {
        AppConfig appConfg = new AppConfig();

        // Authentication/Login
        ApiClient apiClient = ApiClient.getInstance();
        ConduitAuthenticationService conduitAuthenticationService = new ConduitAuthenticationService(apiClient);
        conduitAuthenticationService.authntication(appConfg.getEmail(), appConfg.getPassword());

        //Create connector
        ConduitConnectorService<String, List<ExploreRequest>, List<String>> createConnectorService = new ConduitConnectorService(apiClient);
        if (!createConnectorService.verifyIsUniqueConnectorName(appConfg.getConnectorName())) {
            LOGGER.warn("Connector already exist.");
            System.exit(1);
        }
        ConduitDBRequestService<List<ExploreRequest>> dbRequestService = new ConduitDBRequestService(apiClient);

        dbRequestService.setDbRequest(appConfg.getAuthenticationType(), appConfg.getPassword(), appConfg.getConnectionUrl(), appConfg.getConnectionUsername(), appConfg.getDescription(), appConfg.getConnectorName(), appConfg.getNamespace(), appConfg.getSubscriptionId());
        List<ExploreRequest> dbResponse = dbRequestService.getResponseFromDB(dbRequestService.getDbRequest());
        createConnectorService.setCreateConnectorRequest(dbResponse, dbRequestService.getDbRequest(), appConfg.getTypeName(), appConfg.getTables(), appConfg.getAuthenticationType(), appConfg.getSpecificColumns(), appConfg.getAuthorizationEnabled(), appConfg.getUserSubscription());
        String resultOfCreateConnector = createConnectorService.createConnector().toString();
        LOGGER.info("Create Connector Response: {}", resultOfCreateConnector);

        //Verify if connector was successfully created
        assertTrue(resultOfCreateConnector.contains("HTTP/1.1 200 OK"));

        //REST query
        ConduitRESTQueryService restQueryService = new ConduitRESTQueryService(apiClient);
        restQueryService.verifyExistingConnectors(appConfg.getConnectorName());
        String sql = "SELECT * FROM " + appConfg.getTypeName() + "_" + appConfg.getConnectorName() + "." + appConfg.getTables().get(0) + " limit 10";
        LOGGER.info("SQL RESPONSE {}", restQueryService.executeQuery(sql).toString());

    }
}




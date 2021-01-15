package com.conduit.sample;

import com.conduit.sample.api.ApiClient;
import com.conduit.sample.services.*;

import static org.junit.Assert.*;

import com.conduit.sample.services.entity.ExploreRequest;
import com.conduit.sample.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JDBCApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(JDBCApp.class);

    public static void main(String[] args) throws Exception {
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

        String sql = "SELECT * FROM " + appConfg.getTypeName() + "_" + appConfg.getConnectorName() + "." + appConfg.getTables().get(0);
        ConduitSqlQueryService conduitSqlQueryService = new ConduitSqlQueryService();
        conduitSqlQueryService.executeSqlQuery(sql);

    }
}

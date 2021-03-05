package com.conduit.sample.services;

import com.conduit.sample.api.ApiClient;
import com.conduit.sample.api.requests.CreateConnectorRequest;
import com.conduit.sample.api.requests.DBRequest;
import com.conduit.sample.api.responses.CreateConnectorResponse;
import com.conduit.sample.api.responses.ExistingConectorResponse;
import com.conduit.sample.utils.Routes;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ConduitConnectorService<T, T1, T2> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConduitConnectorService.class);

    private ApiClient client;

    private CreateConnectorRequest createConnectorRequest = new CreateConnectorRequest();

    public ConduitConnectorService(ApiClient client) {
        this.client = client;
    }

    public <T> T createConnector() {
        CreateConnectorResponse<T> createConnectorResponse = new CreateConnectorResponse();
        try {
            return (T) client.post(createConnectorRequest, Routes.metadataDatasource, createConnectorResponse);
        } catch (Exception e) {
            LOGGER.warn("Connector can not be created");
        }
        return null;
    }

    public void setCreateConnectorRequest(T1 response, DBRequest dbRequest, String typeName, List<String> tablesname, String authenticationType, int specificColumns, Boolean isAuthorizationEnabled, String userSubscription) {
        try {
            createConnectorRequest.setCreateConnectorRequest(response, dbRequest, typeName, tablesname, authenticationType, specificColumns, isAuthorizationEnabled, userSubscription);
        } catch (JsonProcessingException e) {
            LOGGER.warn("Connector's fields can not be cast in json format");
        } catch (NullPointerException e) {
            LOGGER.warn("DB response is NULL.");
        }
    }

    public boolean verifyIsUniqueConnectorName(String name) {
        ExistingConectorResponse<T2> existingConectorResponse = new ExistingConectorResponse();
        try {
            List<String> response = (List<String>) client.get(null, Routes.metadataDatasourceNames, existingConectorResponse);
            if (!response.contains(name)) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.warn("Could not extract datasource name ", e);
            System.exit(1);
        }
        return false;
    }

}

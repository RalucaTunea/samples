package com.conduit.sample.services;

import com.conduit.sample.api.ApiClient;
import com.conduit.sample.api.requests.DBRequest;
import com.conduit.sample.api.requests.IRequest;
import com.conduit.sample.api.responses.DBResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConduitDBRequestService<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConduitDBRequestService.class);

    private ApiClient<T> client;

    private DBRequest dbRequest = new DBRequest();

    public DBRequest getDbRequest() {
        return dbRequest;
    }

    public ConduitDBRequestService(ApiClient client) {
        this.client = client;
    }

    public T getResponseFromDB(IRequest dbRequest) {
        try {
            dbRequest.createPayload();
        } catch (JsonProcessingException e) {
            LOGGER.warn("Payload can not be created.");
        }
        DBResponse<T> dbResponse = new DBResponse<>();
        try {
            return client.post(dbRequest, "https://localhost/api/metadata/explore/oracle", dbResponse);
        } catch (Exception e) {
            LOGGER.warn("DB Response can not be created.");
        }
        return null;
    }

    public void setDbRequest(String authenticationType, String connectionPassword, String connectionUrl, String connectionUsername, String description, String name, String namespace, String subscriptionId) {
        try {
            dbRequest.setAuthenticationType(authenticationType);
            dbRequest.setConnectionPassword(connectionPassword);
            dbRequest.setConnectionUrl(connectionUrl);
            dbRequest.setConnectionUsername(connectionUsername);
            dbRequest.setDescription(description);
            dbRequest.setName(name);
            dbRequest.setSubscriptionId(subscriptionId);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn("DB Response can not be created.");
            System.exit(1);
        }

    }

}

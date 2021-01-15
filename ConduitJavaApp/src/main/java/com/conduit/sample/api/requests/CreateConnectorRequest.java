package com.conduit.sample.api.requests;

import com.conduit.sample.services.entity.Connector;
import com.conduit.sample.services.entity.ExploreRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.json.JSONObject;

import java.util.*;

public class CreateConnectorRequest<T> implements IRequest {

    Connector connector = new Connector();
    JSONObject jsonObject = new JSONObject();

    @Override
    public JSONObject getPayload() {
        return jsonObject;
    }

    @Override
    public JSONObject createPayload() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer();
        String json = ow.writeValueAsString(connector);
        jsonObject = new JSONObject(json);
        return jsonObject;
    }

    public void setCreateConnectorRequest(T response, DBRequest dbRequest, String typeName, List<String> tablesname, String authenticationType, int specificColumns, Boolean isAuthorizationEnabled, String userSubscription) throws JsonProcessingException {
        connector.setName(dbRequest.getName());

        List<ExploreRequest> exploreRequest = (List<ExploreRequest>) response;

        connector.setDatabase(exploreRequest.get(0).getName());
        connector.setConnectionUrl(dbRequest.getConnectionUrl());
        connector.setConnectionUsername(dbRequest.getConnectionUsername());
        connector.setConnectionPassword(dbRequest.getConnectionPassword());
        connector.setTypeName(typeName);
        connector.setTableNames(tablesname);
        connector.setAuthenticationType(authenticationType);
        connector.setTablesSchema(exploreRequest.get(0).getTables());
        connector.setStorageLevel();
        connector.setSpecificColumns(specificColumns, exploreRequest.get(0).getTables());
        connector.setAuthenticationRadios();
        connector.setDatabaseValue();
        connector.setDataSourceConnection();
        connector.setUserSubscription(userSubscription);
        connector.setAdSubscriptions();

        if (isAuthorizationEnabled)
            connector.setIsAuthorizationEnabled(true);

        createPayload();
    }

    @Override
    public String toString() {
        return "CreateConnectorRequest{" +
                "connector=" + connector +
                ", jsonObject=" + jsonObject +
                '}';
    }

}

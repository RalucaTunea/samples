package com.conduit.sample.services;

import com.conduit.sample.api.ApiClient;
import com.conduit.sample.api.requests.ExecuteQueryRequest;
import com.conduit.sample.api.responses.ExecuteQueryResponse;
import com.conduit.sample.api.responses.QueryMetadataResponse;

import com.conduit.sample.utils.Roots;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConduitRESTQueryService<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConduitRESTQueryService.class);

    private ApiClient<T> client;

    public ConduitRESTQueryService(ApiClient<T> client) {
        this.client = client;
    }

    public void verifyExistingConnectors(String connectorName) {
        QueryMetadataResponse queryMetadataResponse = new QueryMetadataResponse();
        try {
            QueryMetadataResponse response = ((QueryMetadataResponse) client.get(null, Roots.metadataAll, queryMetadataResponse));
            JSONArray jsonArray = response.getJsonArray();

            boolean find = false;
            for (Object o : jsonArray) {
                JSONObject jsonLineItem = (JSONObject) o;
                String name = jsonLineItem.getString("name");
                if (name.equals("oracle_" + connectorName)) {
                    find = true;
                }
            }
            if (!find) {
                LOGGER.info("Connector can not be find in database.Please create it first.");
                System.exit(1);
            }

        } catch (Exception e) {
            LOGGER.warn("Could not extract datasource name ", e);
            System.exit(1);
        }
    }

    public T executeQuery(String sql) {
        ExecuteQueryRequest executeQueryRequest = new ExecuteQueryRequest();
        executeQueryRequest.setQuery(sql);
        ExecuteQueryResponse<T> executeQueryResponse = new ExecuteQueryResponse();
        try {
            executeQueryRequest.createPayload();
            return client.post(executeQueryRequest, Roots.queryExecute, executeQueryResponse);
        } catch (Exception e) {
            LOGGER.warn("Could not extract sql response ", e);
            System.exit(1);
        }
        return null;
    }


}


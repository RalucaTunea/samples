package com.conduit.sample.api.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.stream.Collectors;

public class ExistingConectorResponse<T> implements IResponse<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExistingConectorResponse.class);

    private T connectorNameList;

    public T getConnectorNameList() {
        return connectorNameList;
    }

    @Override
    public T parseResponse(HttpResponse response) throws JsonProcessingException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                String result = EntityUtils.toString(entity);
                JSONArray connectorArray = new JSONArray(result);
                connectorNameList = (T) connectorArray.toList().stream().map(tableName -> (String) tableName).collect(Collectors.toList());
                return this.connectorNameList;
            } catch (IOException e) {
                LOGGER.warn("Entity may not be null");
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ExistingConectorResponse{" +
                "connectorNameList=" + connectorNameList +
                '}';
    }
}

package com.conduit.sample.api.responses;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ExecuteQueryResponse<T> implements IResponse<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteQueryResponse.class);

    private T jsonObject;
    private String idQuery;
    private String status;

    @Override
    public T parseResponse(HttpResponse response) throws JsonProcessingException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                String result = EntityUtils.toString(entity);
                jsonObject = (T) (new JSONObject(result));
                idQuery = (new JSONObject(result)).get("queryId").toString();
                status = (new JSONObject(result)).get("status").toString();
                return jsonObject;
            } catch (IOException e) {
                LOGGER.warn("Entity may not be null");
            }
        }
        return null;
    }

    public String getIdQuery() {
        return idQuery;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ExecuteQueryResponse{" +
                "jsonObject=" + jsonObject +
                "idQuery=" + idQuery +
                "status=" + status +
                '}';
    }
}


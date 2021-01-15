package com.conduit.sample.api.responses;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.io.IOException;

public class QueryMetadataResponse implements IResponse {
    JSONArray jsonArray = new JSONArray();

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    @Override
    public IResponse parseResponse(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                String result = EntityUtils.toString(entity);
                jsonArray = new JSONArray(result);
                return this;
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "QueryMetadataResponse{" +
                "jsonArray=" + jsonArray +
                '}';
    }
}


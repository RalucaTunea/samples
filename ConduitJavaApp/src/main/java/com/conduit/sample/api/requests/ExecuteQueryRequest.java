package com.conduit.sample.api.requests;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONObject;

public class ExecuteQueryRequest implements IRequest {

    private String queryId = "null";
    private String query;
    private int offset = 0;
    private int limit = 10;
    private JSONObject jsonObject = new JSONObject();

    @Override
    public JSONObject getPayload() {
        return jsonObject;
    }

    @Override
    public JSONObject createPayload() throws JsonProcessingException {
        jsonObject.put("queryId", queryId);
        jsonObject.put("query", query);
        jsonObject.put("offset", offset);
        jsonObject.put("limit", limit);
        return jsonObject;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}



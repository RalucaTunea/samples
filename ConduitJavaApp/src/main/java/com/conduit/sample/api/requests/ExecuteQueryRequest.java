package com.conduit.sample.api.requests;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONObject;

public class ExecuteQueryRequest implements IRequest {

    private String queryId = "null";
    private String query;
    private int pageNumber = 0;
    private int pageSize = 10;
    private JSONObject jsonObject = new JSONObject();

    @Override
    public JSONObject getPayload() {
        return jsonObject;
    }

    @Override
    public JSONObject createPayload() throws JsonProcessingException {
        jsonObject.put("queryId", queryId);
        jsonObject.put("query", query);
        jsonObject.put("pageNumber", pageNumber);
        jsonObject.put("pageSize", pageSize);
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

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}


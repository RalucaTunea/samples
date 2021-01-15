package com.conduit.sample.api.responses;

import com.conduit.sample.api.ApiClient;
import com.conduit.sample.services.entity.ExploreRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class DBResponse<T> implements IResponse<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBResponse.class);

    T exploreRequest;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public T getExploreRequest() {
        return exploreRequest;
    }

    @Override
    public T parseResponse(HttpResponse response) {
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            try {
                String result = EntityUtils.toString(entity);
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ExploreRequest[] g = ApiClient.parseEntityResponse(ExploreRequest[].class, result);
                exploreRequest = (T) Arrays.asList(g);
                return this.exploreRequest;
            } catch (IOException e) {
                LOGGER.warn("Entity may not be null");
                System.exit(1);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "DBResponse{" +
                "exploreRequest=" + exploreRequest +
                '}';
    }
}

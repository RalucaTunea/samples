package com.conduit.sample.api.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.HttpResponse;

public interface IResponse<T> {
    T parseResponse(HttpResponse response) throws JsonProcessingException;
}

package com.conduit.sample.api.requests;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;

public interface IRequest {
    JSONObject getPayload();

    JSONObject createPayload() throws JsonProcessingException;
}

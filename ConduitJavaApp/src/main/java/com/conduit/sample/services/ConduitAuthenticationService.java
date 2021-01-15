package com.conduit.sample.services;

import com.conduit.sample.api.ApiClient;
import com.conduit.sample.api.requests.LoginRequest;
import com.conduit.sample.api.responses.LoginResponse;
import com.conduit.sample.utils.Roots;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class ConduitAuthenticationService<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConduitAuthenticationService.class);
    ApiClient client;

    public ConduitAuthenticationService(ApiClient client) {
        this.client = client;
    }

    public void authntication(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        loginRequest.createPayload();
        LoginResponse<T> loginResponse = new LoginResponse();
        try {
            client.authenticate(loginRequest, Roots.auth, loginResponse);
            String token = (String) loginResponse.getToken();
            client.setToken(token);
            assertTrue(loginResponse.getStatus().equals("HTTP/1.1 200 OK"));
        } catch (Exception e) {
            LOGGER.warn("Connector can not be created");

        }
    }
}

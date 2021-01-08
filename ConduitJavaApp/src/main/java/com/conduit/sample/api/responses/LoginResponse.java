package com.conduit.sample.api.responses;

import com.fasterxml.jackson.core.JsonParseException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoginResponse<T> implements IResponse<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginResponse.class);

    private T token;
    private String status;

    public T getToken() {
        return token;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public T parseResponse(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                String result = EntityUtils.toString(entity);

                JSONObject obj = new JSONObject(result);
                status = response.getStatusLine().toString();
                this.token = (T) obj.get("jwtToken").toString();
                return this.token;
            } catch (JsonParseException | JSONException e) {
                LOGGER.warn("Invalid username or password. Please contact your Admin.");
                System.exit(1);
            } catch (IOException e) {
                LOGGER.info("Authentication token could not be realized. Result from Login is null.");
                System.exit(1);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                '}';
    }
}

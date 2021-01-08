package com.conduit.sample.api.requests;

import org.json.JSONObject;

public class LoginRequest implements IRequest {

    private String email;
    private String password;
    private JSONObject jsonObject = new JSONObject();

    @Override
    public JSONObject getPayload() {
        return jsonObject;
    }


    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public JSONObject createPayload() {
        jsonObject.put("email", email);
        jsonObject.put("password", password);

        return jsonObject;
    }
}

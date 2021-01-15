package com.conduit.sample.api.requests;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

public class DBRequest implements IRequest {
    String authenticationType;
    String connectionPassword;
    String connectionUrl;
    String connectionUsername;
    String description;
    String name;
    String namespace = "default";
    String subscriptionId;

    private JSONObject jsonObject = new JSONObject();

    @Override
    public JSONObject getPayload() {
        return jsonObject;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public void setConnectionPassword(String connectionPassword) {
        this.connectionPassword = connectionPassword;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public void setConnectionUsername(String connectionUsername) {
        this.connectionUsername = connectionUsername;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public String getConnectionPassword() {
        return connectionPassword;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public String getConnectionUsername() {
        return connectionUsername;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public JSONObject createPayload() {
        jsonObject.put("authenticationType", authenticationType);
        byte[] encodedBytes = Base64.encodeBase64(connectionPassword.getBytes());
        jsonObject.put("connectionPassword", new String(encodedBytes));
        jsonObject.put("connectionUrl", connectionUrl);
        jsonObject.put("connectionUsername", connectionUsername);
        jsonObject.put("description", description);
        jsonObject.put("name", name);
        jsonObject.put("namespace", namespace);
        jsonObject.put("subscriptionId", subscriptionId);

        return jsonObject;
    }
}

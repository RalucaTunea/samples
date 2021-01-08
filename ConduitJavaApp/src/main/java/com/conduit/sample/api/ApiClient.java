package com.conduit.sample.api;

import com.conduit.sample.api.requests.IRequest;
import com.conduit.sample.api.responses.IResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class ApiClient<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiClient.class);

    private static String token;
    private static ApiClient client;
    private static ObjectMapper objectMapper = new ObjectMapper();

    private ApiClient() {
    }

    public static ApiClient getInstance() {
        if (client == null) {
            client = new ApiClient();
        }
        return client;
    }

    public void setToken(String token) {
        ApiClient.token = token;
    }

    public String getToken() {
        return token;
    }

    public T authenticate(IRequest req, String url, IResponse<T> response) throws Exception {
        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] chain,
                                     String authType) throws CertificateException {
                return true;
            }
        });

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                builder.build(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(
                sslsf).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

        HttpPost postRequest = new HttpPost(url);

        if (req.getPayload() != null)
            postRequest.setEntity(new StringEntity(req.getPayload().toString()));
        postRequest.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            CloseableHttpResponse httpResponse = client.execute(postRequest);
            return response.parseResponse(httpResponse);

        } catch (IOException e) {
            LOGGER.info("HTTP request may not be null");
        }
        return null;
    }

    public T post(IRequest req, String url, IResponse<T> response) throws Exception {
        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] chain,
                                     String authType) throws CertificateException {
                return true;
            }
        });

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                builder.build(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(
                sslsf).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
        HttpPost postRequest = new HttpPost(url);
        if (req.getPayload() != null)
            postRequest.setEntity(new StringEntity(req.getPayload().toString()));
        postRequest.setHeader("Content-Type", "application/json;charset=UTF-8");
        postRequest.addHeader("Authorization", "Bearer " + token);

        try {
            CloseableHttpResponse httpResponse = client.execute(postRequest);
            return response.parseResponse(httpResponse);

        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("HTTP request may not be null");
        }
        return null;
    }

    public T get(IRequest req, String url, IResponse<T> response) throws Exception {
        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] chain,
                                     String authType) throws CertificateException {
                return true;
            }
        });

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                builder.build(), SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(
                sslsf).setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();
        HttpGet getRequest = new HttpGet(url);
        getRequest.setHeader("Content-Type", "application/json");
        getRequest.addHeader("Authorization", "Bearer " + token);
        try {
            CloseableHttpResponse httpResponse = client.execute(getRequest);

            return response.parseResponse(httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parseEntityResponse(Class<T> classZ, String httpStringEntity) throws JsonProcessingException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(httpStringEntity, classZ);
    }

}

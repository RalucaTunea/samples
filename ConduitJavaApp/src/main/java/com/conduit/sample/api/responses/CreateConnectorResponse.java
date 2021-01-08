package com.conduit.sample.api.responses;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CreateConnectorResponse<T> implements IResponse<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateConnectorResponse.class);

    private T status;

    @Override
    public T parseResponse(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                String result = EntityUtils.toString(entity);
                if (!result.isEmpty())
                    LOGGER.warn(result);
                status = (T) response.getStatusLine().toString();
                return this.status;
            } catch (IOException e) {
                LOGGER.warn("Entity may not be null");
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "CreateConnectorResponse{" +
                "status='" + status + '\'' +
                '}';
    }

}

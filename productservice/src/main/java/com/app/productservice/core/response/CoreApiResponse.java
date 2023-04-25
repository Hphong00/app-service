package com.app.productservice.core;

import com.app.productservice.core.constant.WebCoApiClientConstants;

import java.util.HashMap;
import java.util.Map;

public class CoreApiResponse {
    Map<String, Object> header = new HashMap<>();
    Map<String, Object> body = new HashMap<>();
    Map<String, Object> error = new HashMap<>();

    public Map<String, Object> getHeader() {
        return header;
    }

    public void setHeader(Map<String, Object> header) {
        this.header = header;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    public Map<String, Object> getError() {
        return error;
    }

    public void setError(Map<String, Object> error) {
        this.error = error;
    }

    public void setHeaderAttribute(String attributeKey, Object attributeValue) {
        this.header.put(attributeKey, attributeValue);
    }

    public void setBodyAttribute(String attributeKey, Object attributeValue) {
        this.body.put(attributeKey, attributeValue);
    }

    public void setErrorAttribute(String attributeKey, Object attributeValue) {
        this.error.put(attributeKey, attributeValue);
    }

    public boolean isSuccessRequest() {
        return this.getBody() != null && WebCoApiClientConstants.RESPONSE_OK.equals(this.getBody().get(WebCoApiClientConstants.RESPONSE_STATUS));
    }
}

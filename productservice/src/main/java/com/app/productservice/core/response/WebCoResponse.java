package com.app.productservice.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebCoResponse<T> {
    private String code;
    private String message;
    private String warningCode;
    private String serviceType;
    private T data;

    public WebCoResponse() {
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public WebCoResponse(String code, String message, String serviceType, T data) {
        this.code = code;
        this.message = message;
        this.serviceType = serviceType;
        this.data = data;
    }

    public WebCoResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public WebCoResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWarningCode() {
        return warningCode;
    }

    public void setWarningCode(String warningCode) {
        this.warningCode = warningCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

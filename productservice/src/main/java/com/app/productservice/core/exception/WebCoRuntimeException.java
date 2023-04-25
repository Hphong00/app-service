package com.app.productservice.core.exception;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebCoRuntimeException extends RuntimeException {

    private final String errorCode;

    public WebCoRuntimeException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public WebCoRuntimeException(String message) {
        super(message);
        errorCode = "";
    }

    public WebCoRuntimeException(Throwable cause) {
        super(cause);
        this.errorCode = "";
    }

    public String getErrorCode() {
        if (errorCode == null) {
            return "";
        }
        return errorCode.trim();
    }
}

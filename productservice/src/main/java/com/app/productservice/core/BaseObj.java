package com.app.productservice.core;
import com.app.productservice.core.constant.WebCoApiClientConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

@JsonIgnoreProperties(value = {"errorCode", "errorMessage", "status", "statusOK"}, allowSetters = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseObj extends Extra {
    private static final long serialVersionUID = -5117457068333925068L;
    private String errorCode;
    private String errorMessage;
    private String warningCode;
    private String status;
    private String responseCode;
    private String responseMessage;
    private String serviceType;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getWarningCode() {
        return warningCode;
    }

    public void setWarningCode(String warningCode) {
        this.warningCode = warningCode;
    }

    @Override
    public String toString() {
        return "BaseObj{" +
            "errorCode='" + errorCode + '\'' +
            ", errorMessage='" + errorMessage + '\'' +
            ", status='" + status + '\'' +
            ", responseCode='" + responseCode + '\'' +
            '}';
    }

    public boolean haveError() {
        boolean isHaveError = (StringUtils.isNotBlank(responseCode) && !WebCoApiClientConstants.RESPONSE_CODE_SUCCESS.equals(responseCode)) || !StringUtils.isBlank(this.getErrorCode());
        if (isHaveError && StringUtils.isBlank(this.getErrorCode())) {
            this.errorCode = "SYSTEM_UNAVAILABLE";
        }
        return isHaveError;
    }

    public boolean isStatusOK() {
        boolean isOk = WebCoApiClientConstants.RESPONSE_OK.equalsIgnoreCase(status);
        if (!isOk && StringUtils.isBlank(this.getErrorCode())) {
            this.errorCode = "SYSTEM_UNAVAILABLE";
        }
        return isOk;
    }
}

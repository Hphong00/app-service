package com.app.productservice.core;

import com.app.productservice.core.constant.WebCoApiClientConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Created by ITSOL
 */
@Service
public class WebCoUtils {
    private final Logger logger = LoggerFactory.getLogger(WebCoUtils.class);
    @Autowired
    private MessageSource messageSource;

    public <T> WebCoResponse<T> buildResponse(String code, String message, T data) {
        WebCoResponse<T> response = new WebCoResponse<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public <T> WebCoResponse<T> buildResponse(String code, T data) {
        WebCoResponse<T> response = new WebCoResponse<>();
        response.setCode(code);
        response.setData(data);
        resolveMessage(response);
        return response;
    }

    public <T> WebCoResponse<T> buildResponse(String code) {
        WebCoResponse<T> response = new WebCoResponse<>();
        response.setCode(code);
        response.setData(null);
        resolveMessage(response);
        return response;
    }

    public <T> WebCoResponse<T> buildSuccessResponse(T data) {
        WebCoResponse<T> response = new WebCoResponse<>();
        response.setCode(WebCoApiClientConstants.SIB_CO_SUCCESS_CODE);
        response.setMessage(getMessageI18N(WebCoApiClientConstants.SIB_CO_SUCCESS_MESSAGE));
        response.setData(data);
        return response;
    }

    private <T> WebCoResponse<T> resolveMessage(WebCoResponse<T> response) {
        String messageI18N = getMessageI18N(response.getCode());
        if (!messageI18N.equalsIgnoreCase(response.getCode())) {
            response.setMessage(messageI18N);
        }

        return response;
    }

    public String getMessageI18N(String messageCode) {
        return getMessageI18N(messageCode, null);
    }

    public String getMessageI18N(String messageCode, Object[] parameter) {
        return getMessageI18N(messageCode, LocaleContextHolder.getLocale(), parameter);
    }


    public String getMessageI18N(String messageCode, Locale locale, Object[] parameter) {
        if (messageCode == null) {
            return "";
        }

        try {
            return messageSource.getMessage(messageCode, parameter, locale);
        } catch (Exception ex) {
            logger.debug("SibCoUtils: Cant not find message with code = {}", messageCode);

            return messageCode;
        }
    }
}

package com.app.userservice.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItsolException extends RuntimeException {

    private BaseMessage error;

    public ItsolException() {
    }

    public ItsolException(String message) {
        super(message);
        this.error = new BaseMessage() {
            @Override
            public String getCode() {
                return "0001";
            }

            @Override
            public String getDesc() {
                return "Unhandled error";
            }

            @Override
            public ErrorMessage format(Object... str) {
                return null;
            }
        };
    }

    public ItsolException(BaseMessage error) {
        super(error.getDesc());
        this.error = error;
    }
}

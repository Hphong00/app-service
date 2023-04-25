package com.app.productservice.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString(callSuper = true)
public class BaseDTO implements Serializable {

    private String id;
    private String status;
    private String createdDate;
    private String createdUser;
    private String updatedDate;
    private String updatedUser;

}

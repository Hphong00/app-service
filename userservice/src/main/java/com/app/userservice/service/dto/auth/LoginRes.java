package com.app.userservice.service.dto.auth;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sonba@itsol.vn
 * Date: 24 / 08 / 2022
 * Time: 1:57 PM
 */
@Getter
@Setter
public class LoginRes {

    private String accessToken;

    public LoginRes() {
        //Constructor
    }

    public LoginRes(String accessToken) {
        this.accessToken = accessToken;
    }
}

package com.app.userservice.service;


import com.app.userservice.service.dto.SignUpRequest;

public interface UserService {
    public String signUpUser(SignUpRequest signUpRequest);

}

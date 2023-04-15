package com.app.userservice.service;


import com.app.userservice.service.dto.keycloak.KeycloakUser;
import com.app.userservice.service.dto.LoginRequest;
import org.keycloak.representations.AccessTokenResponse;

public interface KeycloakService {
    public AccessTokenResponse loginWithKeycloak(LoginRequest request);
    public int createUserWithKeycloak(KeycloakUser keycloakUser);
}

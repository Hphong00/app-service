package com.app.userservice.service.impl;

import com.app.userservice.service.KeycloakService;
import com.app.userservice.service.dto.keycloak.KeycloakUser;
import com.app.userservice.service.dto.LoginRequest;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class KeycloakServiceImpl implements KeycloakService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Value("${serverUrl}")
    private String serverUrl;

    @Value("${realm}")
    private String realm;

    @Value("${clientId}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${grantType}")
    private String grantType;

    private final Keycloak keycloak;

    public KeycloakServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public AccessTokenResponse loginWithKeycloak(LoginRequest request) {
        Keycloak loginKeycloak = buildKeycloak(request.getUsername(), request.getPassword());
        AccessTokenResponse accessTokenResponse = null;
        try {
            accessTokenResponse = loginKeycloak.tokenManager().getAccessToken();
            return accessTokenResponse;
        } catch (Exception e) {
            return null;
        }
    }

    private Keycloak buildKeycloak(String username, String password) {
        return KeycloakBuilder.builder().serverUrl(serverUrl).realm(realm).clientId(clientId).clientSecret(clientSecret).username(username).password(password).build();
    }

    @Override
    public int createUserWithKeycloak(KeycloakUser keycloakUser) {

        LOGGER.info("KeycloakServiceImpl | createUserWithKeycloak is started");

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(keycloakUser.getFirstName());
        userRepresentation.setLastName(keycloakUser.getLastName());
        userRepresentation.setEmail(keycloakUser.getEmail());
        userRepresentation.setUsername(keycloakUser.getUsername());
        HashMap<String, List<String>> clientRoles = new HashMap<>();
        clientRoles.put(clientId, Collections.singletonList(keycloakUser.getRole()));
        userRepresentation.setClientRoles(clientRoles);

        userRepresentation.setEnabled(true);

        LOGGER.info("KeycloakServiceImpl | createUserWithKeycloak | userRepresentation is completed");

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType("password");
        credentialRepresentation.setValue(keycloakUser.getPassword());
        credentialRepresentation.setTemporary(false);

        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        LOGGER.info("KeycloakServiceImpl | createUserWithKeycloak | credentialRepresentation is completed");

        UsersResource usersResource = keycloak.realm(realm).users();

        LOGGER.info("KeycloakServiceImpl | createUserWithKeycloak | usersResource : " + usersResource.toString());

        // Create user (requires manage-users role)
        Response response = usersResource.create(userRepresentation);

        LOGGER.info("KeycloakServiceImpl | createUserWithKeycloak | Create User : ");
        LOGGER.info("KeycloakServiceImpl | createUserWithKeycloak | response STATUS : " + response.getStatus());

        String userId = CreatedResponseUtil.getCreatedId(response);
        LOGGER.info("KeycloakServiceImpl | createUserWithKeycloak | userId : " + userId);

        RoleRepresentation savedRoleRepresentation = keycloak.realm(realm).roles().get(keycloakUser.getRole()).toRepresentation();

        keycloak.realm(realm).users().get(userId).roles().realmLevel().add(Arrays.asList(savedRoleRepresentation));

        LOGGER.info("KeycloakServiceImpl | createUserWithKeycloak | Added Role to User");

        return response.getStatus();
    }
}

package com.app.userservice.service.keycloak;

import com.app.userservice.config.keycloak.KeycloakProperties;
import lombok.Getter;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class KCProvider {
    private final static Logger LOGGER = LoggerFactory.getLogger(KCProvider.class);

    @Value("${keycloak-account.admin-user}")
    private String adminUser;

    @Value("${keycloak-account.admin-password}")
    private String adminPassword;

    private Keycloak keycloak = null;
    private final KeycloakProperties keycloakProperties;

    public KCProvider(KeycloakProperties keycloakProperties) {
        this.keycloakProperties = keycloakProperties;
    }

    public Keycloak getInstance() {
        try {
            if (keycloak == null) {
                keycloak = KeycloakBuilder.builder()
                        .serverUrl(keycloakProperties.getAuthServerUrl())
                        .grantType(OAuth2Constants.PASSWORD)
                        .realm(keycloakProperties.getRealm())
                        .clientId(keycloakProperties.getResource())
                        .clientSecret(keycloakProperties.getCredentials().getSecret())
                        .username(adminUser)
                        .password(adminPassword)
                    .resteasyClient(
                        // new ResteasyClientBuilderImpl() // <-- For 4.5.9.Final
                        new ResteasyClientBuilderImpl()
                            .connectionPoolSize(10).build()
                    ).build();
                keycloak.tokenManager().getAccessToken();
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            keycloak = null;
        }

        return keycloak;
    }

    public Keycloak getKeycloak() {
        return keycloak;
    }

    public KeycloakProperties getKeycloakProperties() {
        return keycloakProperties;
    }
}

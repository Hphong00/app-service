package com.app.userservice.config.keycloak;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.admin.client.Keycloak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${serverUrl}")
    private String serverUrl;

    @Value("${realm}")
    private String realm;

    @Value("${clientId}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${userName}")
    private String userName;

    @Value("${password}")
    private String password;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver(){
        return new KeycloakSpringBootConfigResolver();
    }

    @Bean
    public Keycloak keycloak(){
        LOGGER.info("Keycloak connect");
        return Keycloak.getInstance(serverUrl,
                realm,
                userName,
                password,
                clientId,
                clientSecret);
    }
}

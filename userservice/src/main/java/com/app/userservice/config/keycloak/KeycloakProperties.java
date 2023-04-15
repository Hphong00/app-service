package com.app.userservice.config.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by sonba@itsol.vn
 * Date: 25 / 08 / 2022
 * Time: 10:17 AM
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "keycloak", ignoreUnknownFields = true)
public class KeycloakProperties {

    @JsonProperty(value = "auth-server-url")
    private String authServerUrl;
    private String realm;
    private String resource;
    private Credentials credentials;
    public static class Credentials {
        private String secret;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }

    public String getAuthServerUrl() {
        return authServerUrl;
    }

    public void setAuthServerUrl(String authServerUrl) {
        this.authServerUrl = authServerUrl;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}

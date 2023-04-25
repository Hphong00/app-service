package com.app.productservice.core.security;


import com.app.productservice.core.dto.UserInfoKeycloakDTO;
import com.app.productservice.core.constant.WebCoApiClientConstants;
import com.app.productservice.core.exception.WebCoRuntimeException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public class SecurityUtil {

    @SuppressWarnings("unchecked")
    public static String getCurrentUser() {
        Authentication springAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (springAuthentication instanceof KeycloakAuthenticationToken) {
            KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) springAuthentication;
            Principal principal = (Principal) authentication.getPrincipal();
            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            IDToken token = kPrincipal.getKeycloakSecurityContext().getToken();

            return token.getPreferredUsername();
        } else {
            return "ANONYMOUS";
        }
    }

    /**
     * Get token string
     *
     * @return token
     */
    @SuppressWarnings("unchecked")
    public static String getStringToken() {
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Principal principal = (Principal) authentication.getPrincipal();
        KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
        return kPrincipal.getKeycloakSecurityContext().getTokenString();
    }

    @SuppressWarnings("unchecked")
    public static UserInfoKeycloakDTO getUserInfo() {
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new WebCoRuntimeException(WebCoApiClientConstants.AUTH_USER_PASS_INVALID, "Không thành công");
        }
        Principal principal = (Principal) authentication.getPrincipal();
        if (!(principal instanceof KeycloakPrincipal)) {
            throw new WebCoRuntimeException(WebCoApiClientConstants.AUTH_USER_PASS_INVALID, "Không thành công");
        }
        KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
        AccessToken token = kPrincipal.getKeycloakSecurityContext().getToken();
        UserInfoKeycloakDTO userInfoDTO = new UserInfoKeycloakDTO();
        userInfoDTO.setSub(token.getSubject());
        userInfoDTO.setFullName(token.getName());
        userInfoDTO.setUsername(token.getPreferredUsername());
        userInfoDTO.setEmail(token.getEmail());
        if (token.getResourceAccess() != null) {
            token.getResourceAccess().forEach((key, value) -> {
                userInfoDTO.getResourceAccess().put(key, value.getRoles());
            });
        }
        return userInfoDTO;
    }

}

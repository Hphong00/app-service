package com.app.userservice.service.keycloak;

import org.keycloak.representations.idm.UserRepresentation;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Map;

public class KeycloakUtils {

    public static boolean isCurrentAttrSameAsNew(String attrKey, String newValue, UserRepresentation userRepresentation) {
        Map<String, List<String>> attributes = userRepresentation.getAttributes();
        return attributes != null && attributes.containsKey(attrKey)
                && attributes.get(attrKey).stream().anyMatch(currentValue -> currentValue.equalsIgnoreCase(newValue));
    }

    public static String getCreatedIdFromResponse(Response response) {
        URI location = response.getLocation();
        if (!response.getStatusInfo().equals(Response.Status.CREATED)) {
            Response.StatusType statusInfo = response.getStatusInfo();
            throw new RuntimeException("Create method returned status " +
                    statusInfo.getReasonPhrase() + " (Code: " + statusInfo.getStatusCode() + "); expected status: Created (201)");
        }
        if (location == null) {
            return null;
        }
        String path = location.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
}

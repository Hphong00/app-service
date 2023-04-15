package com.app.userservice.service.keycloak;


import com.app.userservice.service.dto.keycloak.KCClientDTO;
import com.app.userservice.service.dto.keycloak.KCRoleDTO;
import com.app.userservice.utils.ItsolException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KCClientService {
    public KCClientService(com.app.userservice.service.keycloak.KCProvider KCProvider) {
        this.KCProvider = KCProvider;
    }

    private final KCProvider KCProvider;

    /**
     * Get client roles
     */
    public List<KCRoleDTO> getClientRoles(String clientId) {
        Keycloak keycloak = KCProvider.getInstance();

        if (keycloak == null)
            throw new ItsolException("Keycloak instance invalid");

        try {
            RealmResource realmResource = keycloak.realm(KCProvider.getKeycloakProperties().getRealm());
            ClientRepresentation clientRepresentation = realmResource.clients()
                    .findAll().stream().filter(client -> client.getClientId().equals(clientId))
                    .collect(Collectors.toList()).get(0);
            ClientResource clientResource = realmResource.clients().get(clientRepresentation.getId());
            return clientResource.roles().list().stream().map(
                    item -> {
                        KCRoleDTO roleDTO = new KCRoleDTO();
                        BeanUtils.copyProperties(item, roleDTO);
                        return roleDTO;
                    }).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ItsolException("UNHANDLED_ERROR");
        }
    }

    /**
     * Get client roles
     */
    public List<KCClientDTO> getClients() {
        Keycloak keycloak = KCProvider.getInstance();

        if (keycloak == null)
            throw new ItsolException("Keycloak instance invalid");

        try {
            ClientsResource clientResource = keycloak.realm(KCProvider.getKeycloakProperties().getRealm()).clients();
            return clientResource.findAll().stream().map(
                    item -> {
                        KCClientDTO clientDTO = new KCClientDTO();
                        BeanUtils.copyProperties(item, clientDTO);
                        return clientDTO;
                    }).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ItsolException("UNHANDLED_ERROR");
        }
    }
}

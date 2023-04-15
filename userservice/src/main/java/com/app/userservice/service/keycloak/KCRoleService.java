package com.app.userservice.service.keycloak;

import com.app.userservice.service.dto.keycloak.KCRoleDTO;
import com.app.userservice.utils.ErrorMessage;
import com.app.userservice.utils.ItsolException;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KCRoleService {
    private final KCProvider kcProvider;
    private final Keycloak keycloak;

    public KCRoleService(KCProvider kcProvider) {
        this.kcProvider = kcProvider;
        this.keycloak = kcProvider.getInstance();
    }

    public void removeUserClientRoles(String userId) {

        if (keycloak == null) {
            throw new ItsolException("Keycloak instance invalid");
        }
        RealmResource realmResource = keycloak.realm(kcProvider.getKeycloakProperties().getRealm());
        UserResource userResource = realmResource.users().get(userId);
        ClientRepresentation clientRepresentation = realmResource.clients()
                .findAll().stream().filter(client -> client.getClientId().equals(kcProvider.getKeycloakProperties().getResource()))
                .collect(Collectors.toList()).get(0);
        userResource.roles().clientLevel(clientRepresentation.getId())
                .remove(userResource.roles().clientLevel(clientRepresentation.getId()).listAll());
    }

    /**
     * Get client role by id
     *
     * @param roleId
     */
    public RoleRepresentation getClientRoleById(String roleId) {


        if (keycloak == null)
            throw new ItsolException("Keycloak instance invalid");

        if (StringUtils.isEmpty(roleId))
            throw new ItsolException("Invalid params");

        try {
            ClientRepresentation clientRepresentation = keycloak.realm(kcProvider.getKeycloakProperties().getRealm()).clients()
                    .findAll().stream().filter(client -> client.getClientId().equals(kcProvider.getKeycloakProperties().getResource()))
                    .collect(Collectors.toList()).get(0);
            ClientResource clientResource = keycloak.realm(kcProvider.getKeycloakProperties().getRealm()).clients().get(clientRepresentation.getId());
            return clientResource.roles().get(roleId).toRepresentation();
        } catch (Exception ex) {
            throw new ItsolException(ErrorMessage.UNHANDLED_ERROR);
        }
    }

    /**
     * Get client roles
     */
    public List<KCRoleDTO> getClientRoles(String clientId) {

        if (keycloak == null)
            throw new ItsolException("Keycloak instance invalid");

        try {
            RealmResource realmResource = keycloak.realm(kcProvider.getKeycloakProperties().getRealm());
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
            throw new ItsolException(ErrorMessage.UNHANDLED_ERROR);
        }
    }

    /**
     * Get realms roles
     */
    public List<KCRoleDTO> getRealmRoles(String roleId) {

        if (keycloak == null)
            throw new ItsolException("Keycloak instance invalid");

        try {
            RealmResource realmResource = keycloak.realm(kcProvider.getKeycloakProperties().getRealm());
            return realmResource.roles().list().stream().map(
                    item -> {
                        KCRoleDTO roleDTO = new KCRoleDTO();
                        BeanUtils.copyProperties(item, roleDTO);
                        return roleDTO;
                    }).collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ItsolException(ErrorMessage.UNHANDLED_ERROR);
        }
    }

    public void assignClientRolesToUser(String userId, String clientId, List<String> roleIds) {
        if (keycloak == null) {
            throw new ItsolException("Keycloak instance invalid");
        }
        RealmResource realmResource = keycloak.realm(kcProvider.getKeycloakProperties().getRealm());
        UsersResource usersResource = realmResource.users();
        UserResource userResource = usersResource.get(userId);
        //getting client
        ClientResource clientResource = realmResource.clients().get(clientId);
        //getting role
        List<RoleRepresentation> lstToAssign = new ArrayList<>();
        roleIds.forEach(item -> {
            List<RoleRepresentation> itemRp = clientResource.roles().list()
                    .stream().filter(element -> element.getId().equals(item)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(itemRp))
                lstToAssign.add(itemRp.get(0));
        });
        //assigning to user
        userResource.roles().clientLevel(clientId).add(lstToAssign);
    }

    public void checkRolesExisted(List<String> roles, String clientId) {
        if (keycloak == null) {
            throw new ItsolException("Keycloak instance invalid");
        }
        RealmResource realmResource = keycloak.realm(kcProvider.getKeycloakProperties().getRealm());

        try {
            roles.forEach(roleItem -> {
                //Get client
                ClientRepresentation clientRepresentation = realmResource.clients()
                        .findAll().stream().filter(client -> client.getId().equals(clientId))
                        .findFirst().orElse(null);
                if (clientRepresentation == null)
                    throw new ItsolException(ErrorMessage.OBJECT_NOT_FOUND.format("Dữ liệu quyền " + roleItem));

                //Get roles
                List<RoleRepresentation> kcRoles = realmResource.clients().get(clientRepresentation.getId()).roles().list();
                RoleRepresentation existed = kcRoles.stream().filter(r -> r.getId().equals(roleItem))
                        .findFirst().orElse(null);

                if (existed == null)
                    throw new ItsolException(ErrorMessage.OBJECT_NOT_FOUND.format("Dữ liệu quyền " + roleItem));
            });
        } catch (Exception ex) {
            if (ex instanceof ItsolException)
                throw ex;
            else
                throw new ItsolException(ErrorMessage.UNHANDLED_ERROR);
        }
    }

}

package com.app.userservice.service.keycloak;

import com.app.userservice.service.dto.RoleDTO;
import com.app.userservice.utils.ErrorMessage;
import com.app.userservice.utils.ItsolException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.ClientMappingsRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.app.userservice.service.keycloak.KeycloakUtils.getCreatedIdFromResponse;


@Service
public class KCGroupService {
    private final KCProvider KCProvider;
    private final KCRoleService kcRoleService;

    public KCGroupService(com.app.userservice.service.keycloak.KCProvider kcProvider, KCRoleService kcRoleService) {
        KCProvider = kcProvider;
        this.kcRoleService = kcRoleService;
    }

    /**
     * @param userId:   Keycloak UserId
     * @param groupIds: Keycloak GroupIds
     */
    public void assignGroupToUser(String userId, List<String> groupIds) {
        Keycloak keycloak = KCProvider.getInstance();
        if (keycloak == null) {
            throw new ItsolException("Keycloak instance invalid");
        }

        RealmResource realmResource = keycloak.realm(KCProvider.getKeycloakProperties().getRealm());
        List<GroupRepresentation> groupRepresentations = realmResource.groups().groups();
        groupIds.forEach(gId -> {
            GroupRepresentation group = groupRepresentations.stream().filter(item -> item.getId().equals(gId)).findFirst().orElse(null);
            if (group == null)
                throw new ItsolException("Group invalid");
        });

        UserResource userResource = realmResource.users().get(userId);
        //Leave all groups
        List<GroupRepresentation> userGroups = userResource.groups();
        userGroups.forEach(item -> userResource.leaveGroup(item.getId()));

        //assign new groups
        groupIds.forEach(userResource::joinGroup);
    }

    public String createGroupByName(String name) {
        Keycloak keycloak = KCProvider.getInstance();
        if (keycloak == null) {
            throw new ItsolException("Keycloak instance invalid");
        }

        try {
            RealmResource realmResource = keycloak.realm(KCProvider.getKeycloakProperties().getRealm());
            GroupRepresentation groupRepresentation = new GroupRepresentation();
            groupRepresentation.setName(name);

            Response response = realmResource.groups().add(groupRepresentation);
            if (Response.Status.CREATED.getStatusCode() == response.getStatus()) {
                return getCreatedIdFromResponse(response);
            } else
                throw new ItsolException("Create group error, code:" + response.getStatus() + ", desc" + response.getStatusInfo().getReasonPhrase());
        } catch (Exception ex) {
            if (ex instanceof ItsolException)
                throw ex;
            else
                throw new ItsolException("UNHANDLED_ERROR");
        }
    }

    /**
     * @param groupId:  Keycloak groupId
     * @param clientId: Keycloak clientId
     * @param roles:    Keycloak roles
     */
    public void updateGroupClientRoles(String groupId, String clientId, List<String> roles) {
        Keycloak keycloak = KCProvider.getInstance();
        if (keycloak == null) {
            throw new ItsolException("Keycloak instance invalid");
        }

        kcRoleService.checkRolesExisted(roles, clientId);

        try {
            RealmResource realmResource = keycloak.realm(KCProvider.getKeycloakProperties().getRealm());

            Map<String, List<RoleRepresentation>> rolesMap = new HashMap<>();

            List<RoleRepresentation> roleMapToGroup = new ArrayList<>();

            List<RoleRepresentation> kcClientRoles = realmResource.clients().get(clientId).roles().list();

            roles.forEach(ro -> {
                kcClientRoles.stream().filter(k -> k.getId().equals(ro)).findFirst()
                        .ifPresent(roleMapToGroup::add);
            });

            rolesMap.put(clientId, roleMapToGroup);

            List<RoleRepresentation> currentGroupRoles = realmResource.groups().group(groupId).roles().clientLevel(clientId).listAll();
            realmResource.groups().group(groupId).roles().clientLevel(clientId).remove(currentGroupRoles);
            realmResource.groups().group(groupId).roles().clientLevel(clientId).add(rolesMap.get(clientId));
        } catch (Exception ex) {
            if (ex instanceof ItsolException)
                throw ex;
            else
                throw new ItsolException("UNHANDLED_ERROR");
        }
    }

    /**
     * @param groupId: Keycloak groupId
     */
    public void deleteGroup(String groupId) {
        Keycloak keycloak = KCProvider.getInstance();
        if (keycloak == null) {
            throw new ItsolException("Keycloak instance invalid");
        }

        try {
            RealmResource realmResource = keycloak.realm(KCProvider.getKeycloakProperties().getRealm());

            GroupResource groupsResource = realmResource.groups().group(groupId);

            //region Check existed user assigned to group => can not delete
            List<UserRepresentation> usersInGroup = groupsResource.members();
            if (!usersInGroup.isEmpty())
                throw new ItsolException(ErrorMessage.OBJECT_CAN_NOT_UPDATE.format("Dữ liệu vai trò"));
            //endregion

            realmResource.groups().group(groupId).remove();
        } catch (Exception ex) {
            throw new ItsolException(ErrorMessage.UNHANDLED_ERROR);
        }
    }

    /**
     * @param id: Keycloak Group Id
     * @return
     */
    public RoleDTO getOneGroup(String id) {
        Keycloak keycloak = KCProvider.getInstance();
        if (keycloak == null) {
            throw new ItsolException("Keycloak instance invalid");
        }

        try {
            RoleDTO result = new RoleDTO();

            RealmResource realmResource = keycloak.realm(KCProvider.getKeycloakProperties().getRealm());

            GroupResource groupResource = realmResource.groups().group(id);
            Map<String, ClientMappingsRepresentation> clientRolesMapping = groupResource.roles().getAll().getClientMappings();
            List<String> permissions = new ArrayList<>();
            for (ClientMappingsRepresentation value : clientRolesMapping.values()) {
                List<RoleRepresentation> roles = value.getMappings();
                roles.forEach(r -> permissions.add(r.getId()));
            }

            result.setId(id);
            result.setRoleName(groupResource.toRepresentation().getName());
            result.setPermissions(permissions);
            return result;
        } catch (Exception ex) {
            throw new ItsolException(ErrorMessage.UNHANDLED_ERROR);
        }
    }

    /**
     * @param userId: Keycloak UserId
     * @return
     */
    public List<RoleDTO> getAllUserGroups(String userId) {
        Keycloak keycloak = KCProvider.getInstance();
        if (keycloak == null) {
            throw new ItsolException("Keycloak instance invalid");
        }
        RealmResource realmResource = keycloak.realm(KCProvider.getKeycloakProperties().getRealm());
        UserResource userResource = realmResource.users().get(userId);
        List<GroupRepresentation> groupRepresentations = userResource.groups();
        return groupRepresentations.stream().map(group ->
                        new RoleDTO(group.getId(), group.getName(), null, null))
                .collect(Collectors.toList());
    }
}

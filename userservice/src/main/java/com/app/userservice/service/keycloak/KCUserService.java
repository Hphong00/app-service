package com.app.userservice.service.keycloak;

import com.app.userservice.service.dto.keycloak.CreateKCUserDTO;
import com.app.userservice.utils.ErrorMessage;
import com.app.userservice.utils.ItsolException;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Nullable;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.app.userservice.service.keycloak.KeycloakUtils.getCreatedIdFromResponse;


@Service
public class KCUserService {
    private static final Logger log = LoggerFactory.getLogger(KCUserService.class);
    private static final int MAX_ID_LENGTH = 8;

    private final KCProvider KCProvider;

    public KCUserService(KCProvider KCProvider) {
        this.KCProvider = KCProvider;
    }

    /**
     * Create new User in config realm and client
     *
     * @param dto DTO
     * @return Keycloack  user id
     */
    public String createUser(CreateKCUserDTO dto) {
        Keycloak keycloak = KCProvider.getInstance();
        if (keycloak == null) {
            throw new ItsolException("Keycloak instance invalid");
        }
        //Create user
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setEmailVerified(true);

        RealmResource realmResource = keycloak.realm(KCProvider.getKeycloakProperties().getRealm());
        UsersResource usersResource = realmResource.users();
        Response response = usersResource.create(user);

        if (Response.Status.CREATED.getStatusCode() == response.getStatus()) {
            //reset password
            UserResource createdUser = usersResource.get(getCreatedIdFromResponse(response));
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(dto.getPassword());
            credential.setTemporary(false);
            createdUser.resetPassword(credential);
            return getCreatedIdFromResponse(response);
        }
        throw new ItsolException("Create user error, code:" + response.getStatus() + ", desc" + response.getStatusInfo().getReasonPhrase());
    }

    /**
     * Delete User in config realm and client by id
     *
     * @param userId
     * @return
     */
    public Response deleteUser(String userId) throws ItsolException {
        Keycloak keycloak = KCProvider.getInstance();
        if (keycloak == null) {
            throw new ItsolException("Keycloak instance invalid");
        }
        // Get realm, user
        RealmResource realmResource = keycloak.realm(KCProvider.getKeycloakProperties().getRealm());
        UsersResource usersResource = realmResource.users();
        Response response = usersResource.delete(userId);
        if (Response.Status.NO_CONTENT.getStatusCode() == response.getStatus()) {
            return response;
        }
        throw new ItsolException("Delete user error, code:" + response.getStatus() + ", desc" + response.getStatusInfo().getReasonPhrase());
    }

    /**
     * Get user by username
     *
     * @param userName Username
     * @return Keycloak User
     */
    @Nullable
    public UserRepresentation getByUserName(String userName) {
        if (ObjectUtils.isEmpty(userName)) {
            throw new ItsolException(ErrorMessage.ARGUMENT_NOT_VALID);
        }
        Keycloak keycloak = KCProvider.getInstance();
        assert keycloak != null;
        try {
            RealmResource realmResource = keycloak.realm(KCProvider.getKeycloakProperties().getRealm());
            List<UserRepresentation> users = realmResource.users().search(userName, true);
            assert users.size() < 2;
            return users.isEmpty() ? null : users.get(0);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new ItsolException(ErrorMessage.UNHANDLED_ERROR);
        }
    }

    /**
     * Update existed User in config realm and client
     */
    public void setUserEnable(String username, Boolean userEnable) {
        Keycloak keycloak = KCProvider.getInstance();
        if (keycloak == null) {
            throw new ItsolException("Keycloak instance invalid");
        }

        if (StringUtils.isEmpty(username)) {
            throw new ItsolException(ErrorMessage.OBJECT_NOT_FOUND.format("Username"));
        }
        try {
            // Get realm, user by username
            UserRepresentation existedUser = getByUserName(username);
            assert existedUser != null;
            existedUser.setEnabled(userEnable);
            RealmResource realmResource = keycloak.realm(KCProvider.getKeycloakProperties().getRealm());
            UserResource usersResource = realmResource.users().get(existedUser.getId());
            usersResource.update(existedUser);
        } catch (Exception ex) {
            throw new ItsolException(ErrorMessage.UNHANDLED_ERROR);
        }
    }
    public void updateEmail(String username, String email) throws ItsolException {
        Keycloak keycloak = KCProvider.getInstance();
        if (keycloak == null) {
            throw new ItsolException("Keycloak instance invalid");
        }

        if (StringUtils.isEmpty(username)) {
            throw new ItsolException(ErrorMessage.OBJECT_NOT_FOUND.format("Username"));
        }
        try {
            // Get realm, user by username
            UserRepresentation existedUser = getByUserName(username);
            assert existedUser != null;
            existedUser.setEmail(email);
            RealmResource realmResource = keycloak.realm(KCProvider.getKeycloakProperties().getRealm());
            UserResource usersResource = realmResource.users().get(existedUser.getId());
            usersResource.update(existedUser);
        } catch (Exception ex) {
            throw new ItsolException(ErrorMessage.UNHANDLED_ERROR);
        }
    }
}

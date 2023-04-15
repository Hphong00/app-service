package com.app.userservice.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@ToString
public class RoleDTO {
    private String id;
    @NotEmpty
    private String roleName;
    @NotEmpty
    private String clientId;
    private List<String> permissions;

    public RoleDTO() {
    }

    public RoleDTO(String id, String roleName, String clientId, List<String> permissions) {
        this.id = id;
        this.roleName = roleName;
        this.clientId = clientId;
        this.permissions = permissions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}

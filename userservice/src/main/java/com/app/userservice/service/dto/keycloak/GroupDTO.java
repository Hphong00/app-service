package com.app.userservice.service.dto.keycloak;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonba@itsol.vn
 * Date: 30 / 09 / 2022
 * Time: 1:58 PM
 */
@Getter
@Setter
public class GroupDTO {

    private String groupId;
    private String groupName;
    private List<ClientDTO> clientRoles;


    public GroupDTO(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.clientRoles = new ArrayList<>();
    }
}

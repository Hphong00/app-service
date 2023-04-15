package com.app.userservice.service.dto.keycloak;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by sonba@itsol.vn
 * Date: 30 / 09 / 2022
 * Time: 2:24 PM
 */
@Getter
@Setter
public class ClientRoleDTO {

    private String key; //ClientId
    private String name; //ClientName
    private String label; //Desc

    public ClientRoleDTO(String key, String name, String label) {
        this.key = key;
        this.name = name;
        this.label = label;
    }
}

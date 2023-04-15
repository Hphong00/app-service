package com.app.userservice.service.dto.keycloak;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonba@itsol.vn
 * Date: 30 / 09 / 2022
 * Time: 2:33 PM
 */
@Getter
@Setter
public class ClientDTO {

    private String key; //ClientId
    private String name; //ClientName
    private String label; //Desc
    private List<ClientRoleDTO> children;

    public ClientDTO() {
        //Constructor
    }

    public ClientDTO(String key, String name, String label) {
        this.key = key;
        this.name = name;
        this.label = label;
        this.children = new ArrayList<>();
    }
}

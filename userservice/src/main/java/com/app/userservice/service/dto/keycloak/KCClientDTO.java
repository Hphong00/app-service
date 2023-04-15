package com.app.userservice.service.dto.keycloak;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class KCClientDTO {
    private String id;
    private String clientId;
    private String name;
    private String description;
    private Map<String, String> attributes;
}

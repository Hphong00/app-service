package com.app.userservice.service.dto.keycloak;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by sonba@itsol.vn
 * Date: 01 / 10 / 2022
 * Time: 3:58 PM
 */
@Getter
@Setter
public class CudGroupReq {

    private String groupId;
    private String groupName;
    private List<String> clientRoleIds;
}

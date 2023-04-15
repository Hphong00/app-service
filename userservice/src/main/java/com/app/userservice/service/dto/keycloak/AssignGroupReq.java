package com.app.userservice.service.dto.keycloak;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Created by sonba@itsol.vn
 * Date: 01 / 10 / 2022
 * Time: 4:35 PM
 */
@Getter
@Setter
public class AssignGroupReq {

    @NotBlank
    private String kcUserId;

    @NotEmpty
    private List<String> groupIds;
}

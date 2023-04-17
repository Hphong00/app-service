package com.app.userservice.web.rest;

import com.app.userservice.config.keycloak.KeycloakProperties;
import com.app.userservice.service.UserService;
import com.app.userservice.service.dto.UserInfoDTO;
import com.app.userservice.service.dto.auth.LoginReq;
import com.app.userservice.service.dto.auth.LoginRes;
import com.app.userservice.utils.ApiResponse;
import com.app.userservice.utils.ErrorMessage;
import com.app.userservice.utils.FileUtils;
import com.app.userservice.utils.ItsolException;
import com.nimbusds.jose.shaded.json.JSONObject;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.util.HttpResponseException;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.netflix.eureka.registry.Key.KeyType.JSON;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticateJWTRest {

    private final KeycloakProperties keycloakProperties;
    private final UserService userService;

    public AuthenticateJWTRest(KeycloakProperties keycloakProperties,
                               UserService userService) {
        this.keycloakProperties = keycloakProperties;
        this.userService = userService;
    }

//    @PostMapping("/login")
//    public ResponseEntity<ApiResponse<LoginRes>> authenticate(@Valid @RequestBody LoginReq loginReq) {
//        String json = JsonF.toString(keycloakProperties);
//        assert json != null;
//        InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
//        try {
//            // create a new instance based on the configuration defined in keycloak.json
//            AuthzClient authzClient = AuthzClient.create(stream);
//
//            // send the authorization request to the server in order to
//            // obtain an RPT with all permissions granted to the user
//            AccessTokenResponse response = authzClient.obtainAccessToken(loginReq.getUsername(), loginReq.getPassword());
//            String rpt = response.getToken();
//            return ResponseEntity.ok(ApiResponse.success(new LoginRes(rpt)));
//        } catch (Exception e) {
//            if (e instanceof HttpResponseException) {
//                throw new ItsolException(ErrorMessage.AUTH_USER_PASS_INVALID);
//            }
//            throw new ItsolException("UNHANDLED_ERRORæ");
//        } finally {
//            FileUtils.safeClose(stream);
//        }
//    }
//    @GetMapping("/user-info")
//    public ResponseEntity<ApiResponse<UserInfoDTO>> getUserInfo() {
//        UserInfoDTO userInfoDTO = userService.getUserInfo();
//        return ResponseEntity.ok(ApiResponse.success(userInfoDTO));
//    }
}

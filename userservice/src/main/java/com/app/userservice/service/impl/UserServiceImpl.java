package com.app.userservice.service.impl;

import com.app.userservice.domain.User;
import com.app.userservice.repository.UserRepository;
import com.app.userservice.service.KeycloakService;
import com.app.userservice.service.UserService;
import com.app.userservice.service.dto.UserInfoDTO;
import com.app.userservice.service.dto.keycloak.KeycloakUser;
import com.app.userservice.service.dto.SignUpRequest;
import com.app.userservice.utils.ItsolException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private ObjectMapper mapper = new ObjectMapper().configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

    @Autowired
    UserRepository userRepository;

    @Autowired
    KeycloakService keycloakService;

    @Override
    public String signUpUser(SignUpRequest signUpRequest) {

        LOGGER.info("UserServiceImpl | signUpUser is started");

        KeycloakUser keycloakUser = new KeycloakUser();
        keycloakUser.setFirstName(signUpRequest.getFirstName());
        keycloakUser.setLastName(signUpRequest.getLastName());
        keycloakUser.setEmail(signUpRequest.getEmail());
        keycloakUser.setPassword(signUpRequest.getPassword());
        keycloakUser.setRole(signUpRequest.getRole());
        keycloakUser.setUsername(signUpRequest.getUsername());

        int status = keycloakService.createUserWithKeycloak(keycloakUser);

        if(status == 201){

            LOGGER.info("UserServiceImpl | signUpUser | status : " + status);

            User user =  mapper.convertValue(keycloakUser, User.class);

            userRepository.save(user);
            return "Sign Up completed";
        }

        return "Not Register";
    }
}

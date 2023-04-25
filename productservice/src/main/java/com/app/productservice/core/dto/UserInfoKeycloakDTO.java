package com.app.productservice.core.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Getter
@Setter
@ToString(callSuper = true)
public class UserInfoKeycloakDTO {
    private String sub;
    private String username;
    private String fullName;
    private String email;
    private Map<String, Set<String>> resourceAccess = new HashMap<>();

    //Info Extra
    private String phoneNumber;
    private String legalId;
    private String dateOfBirth;
    private String avatarUrl;   // ảnh đại diện
    private String address;   // quê quán

    public UserInfoKeycloakDTO() {
        //Constructor ANONYMOUS
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Set<String>> getResourceAccess() {
        return resourceAccess;
    }

    public void setResourceAccess(Map<String, Set<String>> resourceAccess) {
        this.resourceAccess = resourceAccess;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLegalId() {
        return legalId;
    }

    public void setLegalId(String legalId) {
        this.legalId = legalId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

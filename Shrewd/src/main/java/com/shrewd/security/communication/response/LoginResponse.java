package com.shrewd.security.communication.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LoginResponse {
    private String username;
    private List<String> roles;
    private String jwtToken;
    private String tenantId;

    public LoginResponse(String username, List<String> roles, String jwtToken, String tenantId) {
        this.username = username;
        this.roles = roles;
        this.jwtToken = jwtToken;
        this.tenantId = tenantId;
    }

}

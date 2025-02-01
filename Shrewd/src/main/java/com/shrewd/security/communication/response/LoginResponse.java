package com.shrewd.security.communication.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LoginResponse {
//    private String jwtToken;
    private String username;
    private List<String> roles;

    public LoginResponse(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
//        this.jwtToken = jwtToken;
    }

}

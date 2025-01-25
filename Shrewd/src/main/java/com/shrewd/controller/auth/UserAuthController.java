package com.shrewd.controller.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthController {

    @PostMapping("/user/auth/register")
    public String register() {
        return "User Registration";
    }

    @PostMapping("/user/auth/login")
    public String login() {
        return "User Login";
    }

    @PostMapping("/user/auth/logout")
    public String logout() {
        return "User Logout";
    }
}

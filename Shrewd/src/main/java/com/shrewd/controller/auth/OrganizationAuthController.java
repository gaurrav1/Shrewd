package com.shrewd.controller.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationAuthController {

    @PostMapping("/organization/auth/register")
    public String register() {
        return "Organization Registration";
    }

    @PostMapping("/organization/auth/login")
    public String login() {
        return "Organization Login";
    }

    @PostMapping("/organization/auth/logout")
    public String logout() {
        return "Organization Logout";
    }
}

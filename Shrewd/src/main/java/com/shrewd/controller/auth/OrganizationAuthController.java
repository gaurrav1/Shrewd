package com.shrewd.controller.auth;

import com.shrewd.security.communication.request.OrgRegisterRequest;
import com.shrewd.service.implemantation.OrganizationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization/auth")
public class OrganizationAuthController {

    private final OrganizationServiceImpl organizationServiceImpl;

    public OrganizationAuthController(OrganizationServiceImpl organizationServiceImpl) {
        this.organizationServiceImpl = organizationServiceImpl;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody OrgRegisterRequest registerRequest) {
        System.out.println("\n\n\n\n\n\nHehe\n\n");
        return organizationServiceImpl.createOrganization(registerRequest);
    }
}

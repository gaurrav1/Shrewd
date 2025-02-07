package com.shrewd.controller.auth;

import com.shrewd.security.communication.request.OrgRegisterRequest;
import com.shrewd.service.organization.OrganizationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/organization")
public class OrganizationAuthController {

    private final OrganizationService organizationService;

    public OrganizationAuthController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody OrgRegisterRequest registerRequest) {
        return organizationService.createOrganization(registerRequest);
    }

}

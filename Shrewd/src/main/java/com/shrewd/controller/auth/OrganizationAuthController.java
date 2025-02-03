package com.shrewd.controller.auth;

import com.shrewd.security.communication.request.OrgRegisterRequest;
import com.shrewd.service.CreatingOrganization;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization/auth")
public class OrganizationAuthController {

    private final CreatingOrganization creatingOrganization;

    public OrganizationAuthController(CreatingOrganization creatingOrganization) {
        this.creatingOrganization = creatingOrganization;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody OrgRegisterRequest registerRequest) {
        return creatingOrganization.createOrganization(registerRequest);
    }
}

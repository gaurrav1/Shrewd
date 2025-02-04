package com.shrewd.service;

import com.shrewd.security.communication.request.OrgRegisterRequest;
import org.springframework.http.ResponseEntity;

public interface OrganizationService {
    ResponseEntity<?> createOrganization(OrgRegisterRequest registerRequest);
}

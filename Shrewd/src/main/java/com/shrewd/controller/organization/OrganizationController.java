package com.shrewd.controller.organization;

import com.shrewd.dtos.OrganizationDTO;
import com.shrewd.service.organization.OrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping()
    public ResponseEntity<OrganizationDTO> getDetails(@AuthenticationPrincipal UserDetails userDetails) {
        return organizationService.getOrganizationDetails(userDetails);
    }

    @PutMapping()
    public ResponseEntity<OrganizationDTO> updateDetails(@AuthenticationPrincipal UserDetails userDetails, @RequestBody OrganizationDTO organizationDTO) {
        return organizationService.updateOrganizationDetails(userDetails, organizationDTO);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteOrganization(@AuthenticationPrincipal UserDetails userDetails) {
        return organizationService.deleteOrganization(userDetails);
    }
}

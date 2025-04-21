package com.shrewd.model.orgs.service;

import com.shrewd.model.orgs.model.Organization;
import com.shrewd.model.orgs.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

@Service
public class TenantRegistryService {

    private final OrganizationRepository tenantRepository;

    public TenantRegistryService(OrganizationRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public TenantInfo getTenantInfo(String tenantId) {
        Organization tenant = tenantRepository
                .findByTenantIdAndStatus(tenantId, "ACTIVE")
                .orElseThrow(() -> new RuntimeException("Tenant not found or inactive: " + tenantId));
        System.out.println("\n\n\nFound:" + tenant.getJdbcUrl() + "\n\n\n");

        return new TenantInfo(
                tenant.getJdbcUrl(),
                tenant.getUsername(),
                tenant.getPassword()
        );
    }

    public record TenantInfo(String url, String username, String password) {}
}

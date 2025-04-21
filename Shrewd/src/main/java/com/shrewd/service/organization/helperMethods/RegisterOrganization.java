package com.shrewd.service.organization.helperMethods;

import com.shrewd.model.orgs.model.Organization;
import com.shrewd.model.orgs.repository.OrganizationRepository;
import com.shrewd.security.communication.request.OrgRegisterRequest;
import com.shrewd.security.communication.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RegisterOrganization {

    @Value("${spring.datasource.username}")
    private String DB_USERNAME;

    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;

    @Value("${spring.datasource.url}")
    private String DB_URL;

    private final OrganizationRepository organizationRepository;

    public RegisterOrganization(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public boolean isEmailOrTenantExist(OrgRegisterRequest registerRequest) {
        return organizationRepository.existsByEmail(registerRequest.getEmail()) ||
                organizationRepository.existsByTenantId(registerRequest.getTenant());
    }

    public Organization createOrganizationEntity(OrgRegisterRequest registerRequest) {
        Organization organization = new Organization();
        organization.setEmail(registerRequest.getEmail());
        organization.setTenantId(registerRequest.getTenant());
        organization.setOrgName(registerRequest.getOrg_name());
        organization.setPhone(registerRequest.getPhone());
        organization.setAddress(registerRequest.getAddress());
        organization.setJdbcUrl(DB_URL+registerRequest.getTenant());
        organization.setUsername(DB_USERNAME);
        organization.setPassword(DB_PASSWORD);
        organization.setStatus("ACTIVE");
        return organization;
    }

    public RegisterRequest createEmployeeEntity(OrgRegisterRequest registerRequest) {
        RegisterRequest empRegisterRequest = new RegisterRequest();
        empRegisterRequest.setUsername(registerRequest.getUsername());
        empRegisterRequest.setEmail(registerRequest.getEmail());
        empRegisterRequest.setPassword(registerRequest.getPassword());
        empRegisterRequest.setName(registerRequest.getOrg_name());
        empRegisterRequest.setPhone(registerRequest.getPhone());
        return empRegisterRequest;
    }
}

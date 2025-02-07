package com.shrewd.service.organization.helperMethods;

import com.shrewd.service.database.DataSourceService;
import com.shrewd.model.orgs.Organization;
import com.shrewd.repository.orgs.OrganizationRepository;
import com.shrewd.security.communication.request.OrgRegisterRequest;
import com.shrewd.security.communication.request.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class RegisterOrganization {

    private final OrganizationRepository organizationRepository;
    private final DataSourceService dataSourceService;

    public RegisterOrganization(OrganizationRepository organizationRepository,DataSourceService dataSourceService) {
        this.organizationRepository = organizationRepository;
        this.dataSourceService = dataSourceService;
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
        return organization;
    }

    public void initializeTenant(String tenant, RegisterRequest registerRequest) {
        dataSourceService.registerTenantDataSource(tenant, registerRequest);
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

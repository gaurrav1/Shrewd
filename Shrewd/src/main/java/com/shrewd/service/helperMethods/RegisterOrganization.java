package com.shrewd.service.helperMethods;

import com.shrewd.config.DataSourceService;
import com.shrewd.config.hibernate.TenantContext;
import com.shrewd.model.orgs.Organization;
import com.shrewd.repository.orgs.OrganizationRepository;
import com.shrewd.repository.users.roles.RolesRepository;
import com.shrewd.security.communication.request.OrgRegisterRequest;
import com.shrewd.security.communication.request.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterOrganization {

    private final OrganizationRepository organizationRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final DataSourceService dataSourceService;

    public RegisterOrganization(OrganizationRepository organizationRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder, DataSourceService dataSourceService) {
        this.organizationRepository = organizationRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
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
        TenantContext.setCurrentTenant(tenant);
    }

}

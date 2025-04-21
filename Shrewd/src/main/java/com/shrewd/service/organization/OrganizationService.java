package com.shrewd.service.organization;

import com.shrewd.config.TenantContext;
import com.shrewd.dtos.OrganizationDTO;
import com.shrewd.model.orgs.model.Organization;
import com.shrewd.model.orgs.repository.OrganizationRepository;
import com.shrewd.model.users.model.ROLES;
import com.shrewd.model.users.model.Role;
import com.shrewd.model.users.repository.roles.RolesRepository;
import com.shrewd.security.communication.request.OrgRegisterRequest;
import com.shrewd.security.communication.request.RegisterRequest;
import com.shrewd.service.database.DatabaseService;
import com.shrewd.service.organization.helperMethods.RegisterOrganization;
import com.shrewd.service.users.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final DatabaseService databaseService;
    private final RegisterOrganization registerOrganization;
    private final UsersService usersService;
    private final RolesRepository rolesRepository;

    public OrganizationService(OrganizationRepository organizationRepository, DatabaseService databaseService, RegisterOrganization registerOrganization, UsersService usersService, RolesRepository rolesRepository) {
        this.organizationRepository = organizationRepository;
        this.databaseService = databaseService;
        this.registerOrganization = registerOrganization;
        this.usersService = usersService;
        this.rolesRepository = rolesRepository;
    }


    // Creating Organization
    public ResponseEntity<?> createOrganization(OrgRegisterRequest registerRequest) {
        if (registerOrganization.isEmailOrTenantExist(registerRequest)) {
            return ResponseEntity.badRequest().body("Error: Email or Tenant is already in use.");
        }

        String tenant = registerRequest.getTenant();

        // 🔹 Create Database
        boolean createDbStatus = databaseService.createDatabaseForTenant(tenant);
        if (!createDbStatus) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database creation failed/malicious tenant name.");
        }

        try {

            Organization organization = registerOrganization.createOrganizationEntity(registerRequest);
            organizationRepository.save(organization);
            // 🔹 Create Employee and Save
            RegisterRequest empRegisterRequest = registerOrganization.createEmployeeEntity(registerRequest);


            databaseService.triggerHibernateDDL(tenant);
            TenantContext.setTenantId(tenant);
            Arrays.stream(ROLES.values()).forEach(roleEnum -> {
                if (!rolesRepository.findByRoleName(roleEnum).isPresent()) {
                    rolesRepository.save(new Role(roleEnum));
                }
            });

            databaseService.createUsersForTenant(empRegisterRequest);

            TenantContext.clear();
            // 🔹 Save Organization


        } catch (Exception e) {
            databaseService.deleteDatabaseForTenant(tenant);
            throw new RuntimeException("Error during organization setup: " + e.getMessage());
        }

        return ResponseEntity.ok("Organization registered successfully.");
    }


    // Getting organization details
    public ResponseEntity<OrganizationDTO> getOrganizationDetails(UserDetails userDetails) {
        String email = userDetails.getUsername();
        try {
            TenantContext.clear();
            Optional<Organization> organization = organizationRepository.findByEmail(email);
            if (organization.isPresent()) {
                OrganizationDTO orgDTO = new OrganizationDTO();
                orgDTO.setOrgName(organization.get().getOrgName());
                orgDTO.setEmail(organization.get().getEmail());
                orgDTO.setPhone(organization.get().getPhone());
                orgDTO.setAddress(organization.get().getAddress());
                orgDTO.setTenantId(organization.get().getTenantId());
                orgDTO.setCreatedDate(organization.get().getCreatedDate());
                orgDTO.setUpdatedDate(organization.get().getUpdatedDate());
                return ResponseEntity.ok(orgDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching organization details: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OrganizationDTO());
    }


    // Updating organization details
    public ResponseEntity<OrganizationDTO> updateOrganizationDetails(UserDetails userDetails, OrganizationDTO organizationDTO) {

        String email = userDetails.getUsername();
        try {
            TenantContext.clear();
            Optional<Organization> organization = organizationRepository.findByEmail(email);
            if (organization.isPresent()) {
                organization.get().setOrgName(organizationDTO.getOrgName());
                organization.get().setPhone(organizationDTO.getPhone());
                organization.get().setAddress(organizationDTO.getAddress());
                organizationRepository.save(organization.get());

                OrganizationDTO orgDTO = organizationRepository.findByEmail(email).map(org -> {
                    OrganizationDTO dto = new OrganizationDTO();
                    dto.setOrgName(org.getOrgName());
                    dto.setEmail(org.getEmail());
                    dto.setPhone(org.getPhone());
                    dto.setAddress(org.getAddress());
                    dto.setTenantId(org.getTenantId());
                    dto.setCreatedDate(org.getCreatedDate());
                    dto.setUpdatedDate(org.getUpdatedDate());
                    return dto;
                }).orElse(new OrganizationDTO());
                return ResponseEntity.ok(orgDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating organization details: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OrganizationDTO());
    }


    // Deleting organization
    public ResponseEntity<String> deleteOrganization(UserDetails userDetails) {
        String email = userDetails.getUsername();
        try {
            TenantContext.clear();
            Optional<Organization> organization = organizationRepository.findByEmail(email);
            if (organization.isPresent()) {
                organizationRepository.delete(organization.get());
                databaseService.deleteDatabaseForTenant(organization.get().getTenantId());
                return ResponseEntity.ok("Organization deleted successfully.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting organization: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found.");
    }
}

package com.shrewd.service.implemantation;

import com.shrewd.config.DataSourceService;
import com.shrewd.config.DynamicDataSource;
import com.shrewd.config.hibernate.TenantContext;
import com.shrewd.model.orgs.Organization;
import com.shrewd.model.users.Employee;
import com.shrewd.model.users.ROLES;
import com.shrewd.model.users.Role;
import com.shrewd.repository.orgs.OrganizationRepository;
import com.shrewd.repository.users.EmployeeRepository;
import com.shrewd.repository.users.roles.RolesRepository;
import com.shrewd.security.communication.request.OrgRegisterRequest;
import com.shrewd.security.communication.request.RegisterRequest;
import com.shrewd.service.EmployeeService;
import com.shrewd.service.OrganizationService;
import com.shrewd.service.helperMethods.RegisterOrganization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final DatabaseService databaseService;
    private final EmployeeService employeeService;
    private final RegisterOrganization registerOrganization;
    private final DataSourceService dataSourceService;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final DynamicDataSource dynamicDataSource;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, DatabaseService databaseService, EmployeeService employeeService, RegisterOrganization registerOrganization, DataSourceService dataSourceService, RolesRepository rolesRepository, PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository, DynamicDataSource dynamicDataSource) {
        this.organizationRepository = organizationRepository;
        this.databaseService = databaseService;
        this.employeeService = employeeService;
        this.registerOrganization = registerOrganization;
        this.dataSourceService = dataSourceService;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
        this.dynamicDataSource = dynamicDataSource;
    }

//    public ResponseEntity<?> createOrganization(OrgRegisterRequest registerRequest) {
//
//        TenantContext.clear();
//
//
//        if (organizationRepository.existsByEmail(registerRequest.getEmail())) {
//            return ResponseEntity.badRequest().body("Error: Email is already in use.");
//        }
//        if (organizationRepository.existsByTenantId(registerRequest.getTenant())) {
//            return ResponseEntity.badRequest().body("Error: Username is already in use.");
//        }
//
//        Organization organization = new Organization();
//        organization.setEmail(registerRequest.getEmail());
//        organization.setTenantId(registerRequest.getTenant());
//        organization.setOrgName(registerRequest.getOrg_name());
//        organization.setPhone(registerRequest.getPhone());
//        organization.setAddress(registerRequest.getAddress());
//
//        String createDb = databaseService.createDatabaseForTenant(registerRequest.getTenant());
//        if (!createDb.equals("Database created successfully.")) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createDb);
//        }
//
//        try {
//            dataSourceService.registerTenantDataSource(registerRequest.getTenant());
//        } catch (Exception e) {
//            System.out.println("\n\n\n\n\n\n" + e.getMessage());
//        }
//
//        TenantContext.setCurrentTenant(registerRequest.getTenant());
//        dynamicDataSource.determineCurrentLookupKey();
//
//        Employee employee = new Employee();
//        employee.setUsername(registerRequest.getUsername());
//        employee.setEmail(registerRequest.getEmail());
//        employee.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
//        employee.setAccountNonLocked(true);
//        employee.setAccountNonExpired(true);
//        employee.setCredentialsNonExpired(true);
//        employee.setEnabled(true);
//        employee.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
//        employee.setAccountExpiryDate(LocalDate.now().plusYears(1));
//        Set<String> role = new HashSet<>();
//        role.add("ORGANIZATION");
//        empRegisterRequest.setRole(role);
//        employee.setRole(organizationRole);
//
//        employeeRepository.save(employee);
//        TenantContext.clear();
//
//        organizationRepository.save(organization);
//        return ResponseEntity.ok("Organization registered successfully.");
//    }

    public ResponseEntity<?> createOrganization(OrgRegisterRequest registerRequest) {
        if (registerOrganization.isEmailOrTenantExist(registerRequest)) {
            return ResponseEntity.badRequest().body("Error: Email or Tenant is already in use.");
        }

        Organization organization = registerOrganization.createOrganizationEntity(registerRequest);
        String tenant = registerRequest.getTenant();

        // 🔹 Create Database
        String createDbStatus = databaseService.createDatabaseForTenant(tenant);
        if (!createDbStatus.equals("Database created successfully.")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createDbStatus);
        }

        try {
            // 🔹 Create Employee and Save
            RegisterRequest empRegisterRequest = new RegisterRequest();
            empRegisterRequest.setUsername(registerRequest.getUsername());
            empRegisterRequest.setEmail(registerRequest.getEmail());
            empRegisterRequest.setPassword(registerRequest.getPassword());
            empRegisterRequest.setName(registerRequest.getOrg_name());
            empRegisterRequest.setPhone(registerRequest.getPhone());

            Set<String> role = new HashSet<>();
            role.add("ORGANIZATION");
            empRegisterRequest.setRole(role);

            // 🔹 Set Tenant Context and Initialize DB
            registerOrganization.initializeTenant(registerRequest.getTenant(), empRegisterRequest);

            TenantContext.clear();
            // 🔹 Save Organization
            organizationRepository.save(organization);

            // 🔹 Clear Tenant Context
            TenantContext.clear();
        } catch (Exception e) {
            databaseService.deleteDatabaseForTenant(tenant);
            throw new RuntimeException("Error during organization setup: " + e.getMessage());
        }

        return ResponseEntity.ok("Organization registered successfully.");
    }
}

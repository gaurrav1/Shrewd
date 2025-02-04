package com.shrewd.security;

import com.shrewd.config.hibernate.TenantContext;
import com.shrewd.model.orgs.Organization;
import com.shrewd.model.users.Employee;
import com.shrewd.model.users.ROLES;
import com.shrewd.model.users.Role;
import com.shrewd.repository.orgs.OrganizationRepository;
import com.shrewd.repository.users.EmployeeRepository;
import com.shrewd.repository.users.roles.RolesRepository;
import com.shrewd.security.communication.request.OrgRegisterRequest;
import com.shrewd.service.implemantation.OrganizationServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DemoUsers {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Bean
    public CommandLineRunner initData(OrganizationRepository orgRepository, OrganizationServiceImpl organizationServiceImpl, RolesRepository rolesRepository, EmployeeRepository employeeRepository) {
        return args -> {
            System.out.println("\n\n\nInside CommandLineRunner\n\n\n");

            if(!orgRepository.existsByEmail("org1@gmail.com")) {
                OrgRegisterRequest organizationRequest = new OrgRegisterRequest();
                organizationRequest.setUsername("organization1");
                organizationRequest.setPassword("Abcd@1234");
                organizationRequest.setEmail("org1@gmail.com");
                organizationRequest.setTenant("one");
                organizationRequest.setOrg_name("Organization 1");
                organizationRequest.setPhone("1234567890");
                organizationRequest.setAddress("123, Street, City, State, Country");

                organizationServiceImpl.createOrganization(organizationRequest);
            }

            if(!orgRepository.existsByEmail("shrewd@gmail.com")) {
                OrgRegisterRequest organizationRequest2 = new OrgRegisterRequest();
                organizationRequest2.setUsername("shrewdtwo");
                organizationRequest2.setPassword("shrewd@1234");
                organizationRequest2.setEmail("shrewd@gmail.com");
                organizationRequest2.setTenant("two");
                organizationRequest2.setOrg_name("Shrewd Organization");
                organizationRequest2.setPhone("1234567890");
                organizationRequest2.setAddress("123, Street, City, State, Country");

                organizationServiceImpl.createOrganization(organizationRequest2);
            }

            // Fetch the organization after creation
            Organization org1 = orgRepository.findByEmail("org1@gmail.com")
                    .orElseThrow(() -> new RuntimeException("Organization not found"));

            System.out.println("Organization 1: " + org1 + "\n\n\n" + org1.toString());

            TenantContext.setCurrentTenant("one"); // Set tenant context for dynamic datasource

            // Role Initialization for demo employees
            Role adminRole = rolesRepository.findByRoleName(ROLES.ADMIN)
                    .orElseGet(() -> rolesRepository.save(new Role(ROLES.ADMIN)));

            Role hrRole = rolesRepository.findByRoleName(ROLES.HR)
                    .orElseGet(() -> rolesRepository.save(new Role(ROLES.HR)));

            Role managerRole = rolesRepository.findByRoleName(ROLES.MANAGER)
                    .orElseGet(() -> rolesRepository.save(new Role(ROLES.MANAGER)));

            Role employeeRole = rolesRepository.findByRoleName(ROLES.EMPLOYEE)
                    .orElseGet(() -> rolesRepository.save(new Role(ROLES.EMPLOYEE)));

            // Now, set the current tenant using TenantContext to switch to the tenant's database


            // Create and save demo employees (if they don't already exist in the current tenant)
            if (!employeeRepository.existsByEmail("admin@user.com")) {
                Employee admin = new Employee("admin", "admin@user.com", passwordEncoder.encode("admin@1"));
                admin.setCredentialsNonExpired(true);
                admin.setEnabled(true);
                admin.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                admin.setAccountExpiryDate(LocalDate.now().plusYears(1));
                admin.setRole(adminRole);
                employeeRepository.save(admin);
            }

            if (!employeeRepository.existsByEmail("hr@user.com")) {
                Employee hr = new Employee("hr", "hr@user.com", passwordEncoder.encode("hr@12"));
                hr.setAccountNonLocked(false);
                hr.setAccountNonExpired(true);
                hr.setCredentialsNonExpired(true);
                hr.setEnabled(true);
                hr.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                hr.setAccountExpiryDate(LocalDate.now().plusYears(1));
                hr.setRole(hrRole);
                employeeRepository.save(hr);
            }

            TenantContext.setCurrentTenant("two");

            if (!employeeRepository.existsByEmail("manager@user.com")) {
                Employee manager = new Employee("manager", "manager@user.com", passwordEncoder.encode("manager@123"));
                manager.setAccountNonLocked(false);
                manager.setAccountNonExpired(true);
                manager.setCredentialsNonExpired(true);
                manager.setEnabled(true);
                manager.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                manager.setAccountExpiryDate(LocalDate.now().plusYears(1));
                manager.setRole(managerRole);
                employeeRepository.save(manager);
            }

            if (!employeeRepository.existsByEmail("employee@user.com")) {
                Employee employee = new Employee("employee", "employee@user.com", passwordEncoder.encode("employee@1234"));
                employee.setAccountNonLocked(false);
                employee.setAccountNonExpired(true);
                employee.setCredentialsNonExpired(true);
                employee.setEnabled(true);
                employee.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                employee.setAccountExpiryDate(LocalDate.now().plusYears(1));
                employee.setRole(employeeRole);
                employeeRepository.save(employee);
            }

            // Reset TenantContext after operations are complete (Optional)
            TenantContext.clear();
        };
    }
}
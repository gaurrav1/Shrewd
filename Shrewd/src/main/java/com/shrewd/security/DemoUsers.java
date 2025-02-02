//package com.shrewd.security;
//
//import com.shrewd.model.Organization;
//import com.shrewd.model.Employee;
//import com.shrewd.model.Role;
//import com.shrewd.model.ROLES;
//import com.shrewd.repository.OrganizationRepository;
//import com.shrewd.repository.EmployeeRepository;
//import com.shrewd.repository.roles.RolesRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//
//@Component
//public class DemoUsers {
//
//    @Bean
//    public CommandLineRunner initData(OrganizationRepository orgRepository,
//                                      RolesRepository roleRepository,
//                                      EmployeeRepository employeeRepository,
//                                      PasswordEncoder passwordEncoder) {
//        return args -> {
//
//            // Role Initialization
//            Role organizationRole = roleRepository.findByRoleName(ROLES.ORGANIZATION)
//                    .orElseGet(() -> roleRepository.save(new Role(ROLES.ORGANIZATION)));
//
//            Role adminRole = roleRepository.findByRoleName(ROLES.ADMIN)
//                    .orElseGet(() -> roleRepository.save(new Role(ROLES.ADMIN)));
//
//            Role hrRole = roleRepository.findByRoleName(ROLES.HR)
//                    .orElseGet(() -> roleRepository.save(new Role(ROLES.HR)));
//
//            Role managerRole = roleRepository.findByRoleName(ROLES.MANAGER)
//                    .orElseGet(() -> roleRepository.save(new Role(ROLES.MANAGER)));
//
//            Role employeeRole = roleRepository.findByRoleName(ROLES.EMPLOYEE)
//                    .orElseGet(() -> roleRepository.save(new Role(ROLES.EMPLOYEE)));
//
//            // Organization Data
//            if (!orgRepository.existsByEmail("org1@example.com")) {
//                Organization org1 = new Organization("org1", "org1@org.com", passwordEncoder.encode("org@1"));
//                org1.setAccountNonLocked(false);
//                org1.setAccountNonExpired(true);
//                org1.setCredentialsNonExpired(true);
//                org1.setEnabled(true);
//                org1.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
//                org1.setAccountExpiryDate(LocalDate.now().plusYears(1));
//                org1.setTenantId(passwordEncoder.encode("org1"));
//                org1.setUsername("demoOrg");
//                org1.setRole(organizationRole);
//                orgRepository.save(org1);
//
//                // Add Employees to Organization
//                if (!employeeRepository.existsByEmail("admin@user.com")) {
//                    Employee admin = new Employee("admin", "admin@user.com", passwordEncoder.encode("admin@1"));
//                    admin.setCredentialsNonExpired(true);
//                    admin.setEnabled(true);
//                    admin.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
//                    admin.setAccountExpiryDate(LocalDate.now().plusYears(1));
//                    admin.setRole(adminRole);
//                    admin.setOrganization(org1);
//                    employeeRepository.save(admin);
//                }
//
//                if (!employeeRepository.existsByEmail("hr@user.com")) {
//                    Employee hr = new Employee("hr", "hr@user.com", passwordEncoder.encode("hr@12"));
//                    hr.setAccountNonLocked(false);
//                    hr.setAccountNonExpired(true);
//                    hr.setCredentialsNonExpired(true);
//                    hr.setEnabled(true);
//                    hr.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
//                    hr.setAccountExpiryDate(LocalDate.now().plusYears(1));
//                    hr.setRole(hrRole);
//                    hr.setOrganization(org1);
//                    employeeRepository.save(hr);
//                }
//
//                if (!employeeRepository.existsByEmail("manager@user.com")) {
//                    Employee manager = new Employee("manager", "manager@user.com", passwordEncoder.encode("manager@123"));
//                    manager.setAccountNonLocked(false);
//                    manager.setAccountNonExpired(true);
//                    manager.setCredentialsNonExpired(true);
//                    manager.setEnabled(true);
//                    manager.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
//                    manager.setAccountExpiryDate(LocalDate.now().plusYears(1));
//                    manager.setRole(managerRole);
//                    manager.setOrganization(org1);
//                    employeeRepository.save(manager);
//                }
//
//                if (!employeeRepository.existsByEmail("employee@user.com")) {
//                    Employee employee = new Employee("employee", "employee@user.com", passwordEncoder.encode("employee@1234"));
//                    employee.setAccountNonLocked(false);
//                    employee.setAccountNonExpired(true);
//                    employee.setCredentialsNonExpired(true);
//                    employee.setEnabled(true);
//                    employee.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
//                    employee.setAccountExpiryDate(LocalDate.now().plusYears(1));
//                    employee.setRole(employeeRole);
//                    employee.setOrganization(org1);
//                    employeeRepository.save(employee);
//                }
//            }
//        };
//    }
//}

package com.shrewd.security;

import com.shrewd.model.Organization;
import com.shrewd.model.Role;
import com.shrewd.model.ROLES;
import com.shrewd.repository.OrganizationRepository;
import com.shrewd.repository.EmployeeRepository;
import com.shrewd.repository.roles.RolesRepository;
import com.shrewd.tenantConfig.service.DataSourceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

@Component
public class DemoUsers {

    @Bean
    public CommandLineRunner initData(OrganizationRepository orgRepository,
                                      RolesRepository roleRepository,
                                      EmployeeRepository employeeRepository,
                                      PasswordEncoder passwordEncoder,
                                      DataSourceService dataSourceService) { // Inject DataSourceService
        return args -> {

            // Role Initialization
            Role organizationRole = roleRepository.findByRoleName(ROLES.ORGANIZATION)
                    .orElseGet(() -> roleRepository.save(new Role(ROLES.ORGANIZATION)));

            Role adminRole = roleRepository.findByRoleName(ROLES.ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(ROLES.ADMIN)));

            Role hrRole = roleRepository.findByRoleName(ROLES.HR)
                    .orElseGet(() -> roleRepository.save(new Role(ROLES.HR)));

            Role managerRole = roleRepository.findByRoleName(ROLES.MANAGER)
                    .orElseGet(() -> roleRepository.save(new Role(ROLES.MANAGER)));

            Role employeeRole = roleRepository.findByRoleName(ROLES.EMPLOYEE)
                    .orElseGet(() -> roleRepository.save(new Role(ROLES.EMPLOYEE)));

            // Organization Data
            if (!orgRepository.existsByEmail("org1@org.com")) {
                Organization org1 = new Organization("org1", "org1@org.com", passwordEncoder.encode("org@1"));
                org1.setAccountNonLocked(false);
                org1.setAccountNonExpired(true);
                org1.setCredentialsNonExpired(true);
                org1.setEnabled(true);
                org1.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                org1.setAccountExpiryDate(LocalDate.now().plusYears(1));
                org1.setTenantId(passwordEncoder.encode("org1"));
                org1.setUsername("demoOrg");

                // Save the Organization to the master database (shrewd DB)
                System.out.println("\n\nIn DemoUser\n\n");
                String createDatabaseQuery = "CREATE DATABASE org_" + "demoOrg";
                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "gaurav", "NGaurav@113")) {
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate(createDatabaseQuery);
                    stmt.close();
                    orgRepository.save(org1);
                    System.out.println("\n\nDatabase did created\n\n");
                } catch (SQLException e) {
                    System.out.println("\n\nDatabase did not created\n\n");
                    throw new RuntimeException(e.getMessage());
                }

                try {
                    dataSourceService.registerTenantDataSource("demoOrg");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

//                // Step 1: Create the tenant database (organization's database)
//                String tenantId = org1.getTenantId(); // Get the tenant ID
//                String username = org1.getUsername(); // Organization's username
//                String password = "tenantDbPassword"; // Password for the organization database
//                dataSourceService.createAndRegisterTenantDataSource(tenantId, username, password);
//
//                // Step 2: Now add employees to this organization, but save them to the organization-specific database
//                // Employee Data
//                if (!employeeRepository.existsByEmail("admin@user.com")) {
//                    // Switch to the new organization's database
//                    dataSourceService.setTenantDataSource(tenantId); // Dynamically switch the data source
//
//                    Employee admin = new Employee("admin", "admin@user.com", passwordEncoder.encode("admin@1"));
//                    admin.setCredentialsNonExpired(true);
//                    admin.setEnabled(true);
//                    admin.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
//                    admin.setAccountExpiryDate(LocalDate.now().plusYears(1));
//                    admin.setRole(adminRole);
//                    admin.setOrganization(org1);
//                    employeeRepository.save(admin);
//                }
//
//                if (!employeeRepository.existsByEmail("hr@user.com")) {
//                    // Switch to the new organization's database
//                    dataSourceService.setTenantDataSource(tenantId); // Dynamically switch the data source
//
//                    Employee hr = new Employee("hr", "hr@user.com", passwordEncoder.encode("hr@12"));
//                    hr.setAccountNonLocked(false);
//                    hr.setAccountNonExpired(true);
//                    hr.setCredentialsNonExpired(true);
//                    hr.setEnabled(true);
//                    hr.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
//                    hr.setAccountExpiryDate(LocalDate.now().plusYears(1));
//                    hr.setRole(hrRole);
//                    hr.setOrganization(org1);
//                    employeeRepository.save(hr);
//                }
//
//                if (!employeeRepository.existsByEmail("manager@user.com")) {
//                    // Switch to the new organization's database
//                    dataSourceService.setTenantDataSource(tenantId); // Dynamically switch the data source
//
//                    Employee manager = new Employee("manager", "manager@user.com", passwordEncoder.encode("manager@123"));
//                    manager.setAccountNonLocked(false);
//                    manager.setAccountNonExpired(true);
//                    manager.setCredentialsNonExpired(true);
//                    manager.setEnabled(true);
//                    manager.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
//                    manager.setAccountExpiryDate(LocalDate.now().plusYears(1));
//                    manager.setRole(managerRole);
//                    manager.setOrganization(org1);
//                    employeeRepository.save(manager);
//                }
//
//                if (!employeeRepository.existsByEmail("employee@user.com")) {
//                    // Switch to the new organization's database
//                    dataSourceService.setTenantDataSource(tenantId); // Dynamically switch the data source
//
//                    Employee employee = new Employee("employee", "employee@user.com", passwordEncoder.encode("employee@1234"));
//                    employee.setAccountNonLocked(false);
//                    employee.setAccountNonExpired(true);
//                    employee.setCredentialsNonExpired(true);
//                    employee.setEnabled(true);
//                    employee.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
//                    employee.setAccountExpiryDate(LocalDate.now().plusYears(1));
//                    employee.setRole(employeeRole);
//                    employee.setOrganization(org1);
//                    employeeRepository.save(employee);
//                }
            }
        };
    }
}

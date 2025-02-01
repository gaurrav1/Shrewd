package com.shrewd.security;

import com.shrewd.model.Organization;
import com.shrewd.model.Employee;
import com.shrewd.model.roles.Role;
import com.shrewd.model.roles.ROLES;
import com.shrewd.repository.OrganizationRepository;
import com.shrewd.repository.EmployeeRepository;
import com.shrewd.repository.roles.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DemoUsers {

    @Bean
    public CommandLineRunner initData(OrganizationRepository orgRepository,
                                      RolesRepository roleRepository,
                                      EmployeeRepository employeeRepository,
                                      PasswordEncoder passwordEncoder) {
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
            if (!orgRepository.existsByEmail("org1@example.com")) {
                Organization org1 = new Organization("org1", "org1@org.com", passwordEncoder.encode("org@1"));
                org1.setAccountNonLocked(false);
                org1.setAccountNonExpired(true);
                org1.setCredentialsNonExpired(true);
                org1.setEnabled(true);
                org1.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                org1.setAccountExpiryDate(LocalDate.now().plusYears(1));
                org1.setRole(organizationRole);
                orgRepository.save(org1);

                // Add Employees to Organization
                if (!employeeRepository.existsByEmail("admin@user.com")) {
                    Employee admin = new Employee("admin", "admin@user.com", passwordEncoder.encode("admin@1"));
                    admin.setCredentialsNonExpired(true);
                    admin.setEnabled(true);
                    admin.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                    admin.setAccountExpiryDate(LocalDate.now().plusYears(1));
                    admin.setRole(adminRole);
                    admin.setOrganization(org1);
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
                    hr.setOrganization(org1);
                    employeeRepository.save(hr);
                }

                if (!employeeRepository.existsByEmail("manager@user.com")) {
                    Employee manager = new Employee("manager", "manager@user.com", passwordEncoder.encode("manager@123"));
                    manager.setAccountNonLocked(false);
                    manager.setAccountNonExpired(true);
                    manager.setCredentialsNonExpired(true);
                    manager.setEnabled(true);
                    manager.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                    manager.setAccountExpiryDate(LocalDate.now().plusYears(1));
                    manager.setRole(managerRole);
                    manager.setOrganization(org1);
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
                    employee.setOrganization(org1);
                    employeeRepository.save(employee);
                }
            }
        };
    }
}
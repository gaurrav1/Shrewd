package com.shrewd.security;

import com.shrewd.model.Organization;
import com.shrewd.model.User;
import com.shrewd.model.roles.OrgRole;
import com.shrewd.model.roles.OrgRoles;
import com.shrewd.model.roles.UserRole;
import com.shrewd.model.roles.UserRoles;
import com.shrewd.repository.OrganizationRepository;
import com.shrewd.repository.UserRepository;
import com.shrewd.repository.roles.OrganizationRolesRepository;
import com.shrewd.repository.roles.UserRolesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DemoUsers {
    @Bean
    public CommandLineRunner initData(OrganizationRolesRepository orgRoleRepository,
                                      OrganizationRepository orgRepository,
                                      UserRolesRepository userRoleRepository,
                                      UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {

            // Organization Data
            OrgRole userOrgRole = orgRoleRepository.findByRoleName(OrgRoles.ORGANIZATION)
                    .orElseGet(() -> orgRoleRepository.save(new OrgRole(OrgRoles.ORGANIZATION)));

            if (!orgRepository.existsByEmail("org1@example.com")) {
                Organization org1 = new Organization("org1", "org1@example.com", passwordEncoder.encode("abcd1234"));
                org1.setAccountNonLocked(false);
                org1.setAccountNonExpired(true);
                org1.setCredentialsNonExpired(true);
                org1.setEnabled(true);
                org1.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                org1.setAccountExpiryDate(LocalDate.now().plusYears(1));
                org1.setOrgRole(userOrgRole);
                orgRepository.save(org1);
            }

            // User Data
            UserRole adminRole = userRoleRepository.findByRoleName(UserRoles.ADMIN)
                    .orElseGet(() -> userRoleRepository.save(new UserRole(UserRoles.ADMIN)));

            UserRole hrRole = userRoleRepository.findByRoleName(UserRoles.HR)
                    .orElseGet(() -> userRoleRepository.save(new UserRole(UserRoles.HR)));

            UserRole managerRole = userRoleRepository.findByRoleName(UserRoles.MANAGER)
                    .orElseGet(() -> userRoleRepository.save(new UserRole(UserRoles.MANAGER)));

            UserRole employeeRole = userRoleRepository.findByRoleName(UserRoles.EMPLOYEE)
                    .orElseGet(() -> userRoleRepository.save(new UserRole(UserRoles.EMPLOYEE)));

            if (!userRepository.existsByEmail("admin@user.com")) {
                User admin = new User("admin", "admin@user.com", passwordEncoder.encode("admin@1"));
                admin.setAccountNonLocked(false);
                admin.setAccountNonExpired(true);
                admin.setCredentialsNonExpired(true);
                admin.setEnabled(true);
                admin.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                admin.setAccountExpiryDate(LocalDate.now().plusYears(1));
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
            if (!userRepository.existsByEmail("hr@user.com")) {
                User hr = new User("hr", "hr@user.com", passwordEncoder.encode("hr@12"));
                hr.setAccountNonLocked(false);
                hr.setAccountNonExpired(true);
                hr.setCredentialsNonExpired(true);
                hr.setEnabled(true);
                hr.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                hr.setAccountExpiryDate(LocalDate.now().plusYears(1));
                hr.setRole(hrRole);
                userRepository.save(hr);
            }
            if (!userRepository.existsByEmail("manager@user.com")) {
                User manager = new User("manager", "manager@user.com", passwordEncoder.encode("manager@123"));
                manager.setAccountNonLocked(false);
                manager.setAccountNonExpired(true);
                manager.setCredentialsNonExpired(true);
                manager.setEnabled(true);
                manager.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                manager.setAccountExpiryDate(LocalDate.now().plusYears(1));
                manager.setRole(managerRole);
                userRepository.save(manager);
            }
            if (!userRepository.existsByEmail("employee@user.com")) {
                User employee = new User("employee", "employee@user.com", passwordEncoder.encode("employee@1234"));
                employee.setAccountNonLocked(false);
                employee.setAccountNonExpired(true);
                employee.setCredentialsNonExpired(true);
                employee.setEnabled(true);
                employee.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
                employee.setAccountExpiryDate(LocalDate.now().plusYears(1));
                employee.setRole(employeeRole);
                userRepository.save(employee);
            }

        };
    }
}

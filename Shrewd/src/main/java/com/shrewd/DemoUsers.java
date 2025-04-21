package com.shrewd;

import com.shrewd.model.orgs.repository.OrganizationRepository;
import com.shrewd.model.users.repository.UsersRepository;
import com.shrewd.model.users.repository.roles.RolesRepository;
import com.shrewd.security.communication.request.OrgRegisterRequest;
import com.shrewd.service.organization.OrganizationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DemoUsers {

    @Bean
    public CommandLineRunner initData(OrganizationRepository orgRepository, OrganizationService organizationService, RolesRepository rolesRepository, UsersRepository usersRepository) {
        return args -> {
            System.out.println("\n\n\nInside CommandLineRunner\n\n\n");

            if(!orgRepository.existsByEmail("org1@gmail.com")) {
                OrgRegisterRequest organizationRequest = getOrgRegisterRequest();
                organizationService.createOrganization(organizationRequest);
            }

            if(!orgRepository.existsByEmail("shrewd@gmail.com")) {
                OrgRegisterRequest organizationRequest2 = getOrganizationRequest2();
                organizationService.createOrganization(organizationRequest2);
            }

            System.out.println("\n\n\nExiting CommandLineRunner\n\n\n");
        };
    }

    private static OrgRegisterRequest getOrganizationRequest2() {
        OrgRegisterRequest organizationRequest2 = new OrgRegisterRequest();
        organizationRequest2.setUsername("shrewdtwo");
        organizationRequest2.setPassword("shrewd@1234");
        organizationRequest2.setEmail("shrewd@gmail.com");
        organizationRequest2.setTenant("two");
        organizationRequest2.setOrg_name("Shrewd Organization");
        organizationRequest2.setPhone("1234567890");
        organizationRequest2.setAddress("123, Street, City, State, Country");
        return organizationRequest2;
    }

    private static OrgRegisterRequest getOrgRegisterRequest() {
        OrgRegisterRequest organizationRequest = new OrgRegisterRequest();
        organizationRequest.setUsername("organization1");
        organizationRequest.setPassword("Abcd@1234");
        organizationRequest.setEmail("org1@gmail.com");
        organizationRequest.setTenant("one");
        organizationRequest.setOrg_name("Organization 1");
        organizationRequest.setPhone("1234567890");
        organizationRequest.setAddress("123, Street, City, State, Country");
        return organizationRequest;
    }
}
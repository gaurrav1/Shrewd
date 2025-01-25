package com.shrewd.security.service;

import com.shrewd.model.Organization;
import com.shrewd.repository.OrganizationRepository;
import com.shrewd.security.service.org.OrgDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OrgDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Organization organization = organizationRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Organization not found with email: " + username));
        System.out.println("In OrgDetailsServiceImpl.java");
        return OrgDetailsImpl.build(organization);
    }
}
package com.shrewd.security.service;

import com.shrewd.model.User;
import com.shrewd.repository.EmployeeRepository;
import com.shrewd.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User employee = employeeRepository.findByEmail(username).orElse(null);

        if (employee != null) {
            System.out.println("Found in Employee");
            return UserDetailsImpl.build(employee);
        }

        throw new UsernameNotFoundException("User not found with email: " + username);
    }
}

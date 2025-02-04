package com.shrewd.security.service;

import com.shrewd.model.users.User;
import com.shrewd.repository.users.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public CustomUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User employee = employeeRepository.findByEmail(username).orElse(null);

        if (employee != null) {
            return UserDetailsImpl.build(employee);
        }

        throw new UsernameNotFoundException("User not found with email: " + username);
    }
}

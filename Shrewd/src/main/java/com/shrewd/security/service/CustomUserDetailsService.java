package com.shrewd.security.service;

import com.shrewd.model.users.model.User;
import com.shrewd.model.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User employee = usersRepository.findByEmail(username).orElse(null);

        if (employee != null) {
            return UserDetailsImpl.build(employee);
        }

        throw new UsernameNotFoundException("User not found with email: " + username);
    }
}

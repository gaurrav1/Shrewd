package com.shrewd.security.service;

import com.shrewd.model.users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authority;

    private UserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
        System.out.println("In UserDetailsImpl.java");

    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
package com.shrewd.security.service.org;

import com.shrewd.model.Organization;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class OrgDetailsImpl implements UserDetails {

    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authority;

    private OrgDetailsImpl(String username, String password,Collection<? extends GrantedAuthority> authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
        System.out.println("In OrgDetailsImpl.java");

    }

    public static OrgDetailsImpl build(Organization organization) {
        GrantedAuthority authority = new SimpleGrantedAuthority(organization.getOrgRole().getRoleName().name());
        return new OrgDetailsImpl(
                organization.getEmail(),
                organization.getPassword(),
                List.of(authority)
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
package com.shrewd.tenantConfig;

import com.shrewd.model.Organization;
import com.shrewd.repository.OrganizationRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
public class TenantFilter extends OncePerRequestFilter {

    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TenantFilter(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(12);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tenantId = extractTenantIdFromToken(request.getHeader("X-OrgId"));

        if (tenantId != null) {
            Organization organization = organizationRepository.findByTenantId(tenantId);

            if (organization != null) {
                TenantContext.setCurrentTenant(organization.getUsername());
            } else {
                System.out.println("Organization not found");
            }
        } else {
            System.out.println("Tenant token not found");
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear(); // Always revert to master DB after request
        }
    }
    private String extractTenantIdFromToken(String token) {
        return passwordEncoder.encode(token);
    }
}

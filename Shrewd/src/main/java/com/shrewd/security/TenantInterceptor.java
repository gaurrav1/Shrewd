package com.shrewd.security;

import com.shrewd.config.hibernate.TenantContext;
import com.shrewd.repository.orgs.OrganizationRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class TenantInterceptor implements Filter {

    private final OrganizationRepository organizationRepository;

    public TenantInterceptor(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestPath = httpServletRequest.getRequestURI();

        List<String> EXCLUDED_PATHS = List.of("/organization/auth");

        if (EXCLUDED_PATHS.stream().anyMatch(requestPath::startsWith)) {
            // Skip tenant validation for these paths
            chain.doFilter(request, response);
            return;
        }
        // Retrieve tenantId from the request header
        String tenantId = httpServletRequest.getHeader("X-Organization-ID");

        if (tenantId == null || tenantId.isEmpty()) {
            // If tenant ID is missing, respond with an error
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.getWriter().write("You don't belong to any organization. Please provide a valid Tenant ID.");
            return; // Stop further processing
        }

        // Check if the tenant ID is valid (exists in the organization repository)
        boolean tenantExists = organizationRepository.existsByUsername(tenantId); // Assuming existsById is a valid method
        if (!tenantExists) {
            // If tenant ID is invalid, respond with an error
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            httpServletResponse.getWriter().write("Invalid Tenant ID. You don't belong to any organization.");
            return; // Stop further processing
        }

        // Set the tenant context for further processing
        TenantContext.setCurrentTenant(tenantId);

        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}

package com.shrewd.security;

import com.shrewd.config.hibernate.TenantContext;
import com.shrewd.repository.orgs.OrganizationRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        System.out.println("\n\n\nAAAAAAAAAAAA\n\n\n");
        List<String> EXCLUDED_PATHS = List.of("/organization/auth");

        System.out.println("\n\n\nBBBBBBBBBBBBBB\n\n\n");
        if (EXCLUDED_PATHS.stream().anyMatch(requestPath::startsWith)) {
            System.out.println("\n\n\n BYPASSING TENANT INTERCEPTOR \n\n\n");
            chain.doFilter(request, response);
            return;
        }
        System.out.println("\n\n\nCCCCCCCCCCCCCCCCC\n\n\n");
        String tenantId = httpServletRequest.getHeader("X-Organization-ID");

        if (tenantId == null || tenantId.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.getWriter().write("You don't belong to any organization. Please provide a valid Tenant ID.");
            return;
        }
        System.out.println("\n\n\nDDDDDDDDDDDDDD\n\n\n");
        boolean tenantExists = organizationRepository.existsByTenantId(tenantId);
        if (!tenantExists) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            httpServletResponse.getWriter().write("Invalid Tenant ID. You don't belong to any organization.");
            return;
        }

        TenantContext.setCurrentTenant(tenantId);

        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}

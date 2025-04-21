package com.shrewd.security.tenant;

import com.shrewd.config.TenantContext;
import com.shrewd.model.orgs.repository.OrganizationRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class TenantFilter implements Filter {

    private final OrganizationRepository organizationRepository;

    public TenantFilter(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestPath = httpServletRequest.getRequestURI();
        System.out.println("\n\nIm here tenant filter\n\n");
        List<String> EXCLUDED_PATHS = List.of("/auth/organization/register","/auth/csrf/csrf-token");


        if (EXCLUDED_PATHS.stream().anyMatch(requestPath::startsWith)) {
            System.out.println("\n\n\n BYPASSING TENANT INTERCEPTOR \n\n\n");
            chain.doFilter(request, response);
            return;
        }

        String tenantId = httpServletRequest.getHeader("X-Organization-ID");

        if (tenantId == null || tenantId.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpServletResponse.getWriter().write("Tenant ID missing in header.");
            return;
        }

        boolean tenantExists = organizationRepository.existsByTenantId(tenantId);
        if (!tenantExists) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            httpServletResponse.getWriter().write("Invalid Tenant ID. Please provide legitimate tenant ID.");
            return;
        }

        TenantContext.setTenantId(tenantId);
        System.out.println("\n\n\n TENANT CONTEXT SET TO: " + TenantContext.getTenantId() + "\n\n\n");

        try {
            chain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}

package com.shrewd.controller.auth;

import com.shrewd.model.Organization;
import com.shrewd.repository.OrganizationRepository;
import com.shrewd.security.communication.request.LoginRequest;
import com.shrewd.security.communication.request.OrgRegisterRequest;
import com.shrewd.security.communication.response.LoginResponse;
import com.shrewd.tenantConfig.service.DataSourceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/organization/auth")
public class OrganizationAuthController {

    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final DataSourceService dataSourceService;
    private final JdbcTemplate jdbcTemplate;

    public OrganizationAuthController(
            OrganizationRepository organizationRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, DataSourceService dataSourceService, JdbcTemplate jdbcTemplate) {
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.dataSourceService = dataSourceService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody OrgRegisterRequest registerRequest) {
        if (organizationRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use.");
        }
        if (organizationRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already in use.");
        }

        Organization organization = new Organization(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword())
        );
        organization.setAccountNonLocked(true);
        organization.setAccountNonExpired(true);
        organization.setCredentialsNonExpired(true);
        organization.setEnabled(true);
        organization.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
        organization.setAccountExpiryDate(LocalDate.now().plusYears(1));
        organization.setTenantId(passwordEncoder.encode(registerRequest.getTenant()));
        organization.setUsername(registerRequest.getUsername());



//        String tenantId = registerRequest.getUsername();
//        String createDatabaseQuery = "CREATE DATABASE org_" + tenantId;
//        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "gaurav", "NGaurav@113")) {
//            Statement stmt = connection.createStatement();
//            stmt.executeUpdate(createDatabaseQuery);
//            stmt.close();
//        } catch (SQLException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Failed to create tenant database.");
//        }

        String createDb = createTenantDatabase(registerRequest.getUsername());
        if (!createDb.equals("Database created successfully.")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createDb);
        }

        try {
            dataSourceService.registerTenantDataSource(registerRequest.getUsername());
        } catch (Exception e) {
            System.out.println("\n\n\n\n\n\n" + e.getMessage());
        }

        organizationRepository.save(organization);
        return ResponseEntity.ok("Organization registered successfully.");
    }

    public String createTenantDatabase(String username) {
        String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS org_" + username;

        try {
            jdbcTemplate.execute(createDatabaseQuery);
            return "Database created successfully.";
        } catch (Exception e) {
            return "Error: Failed to create tenant database.";
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            LoginResponse response = new LoginResponse(userDetails.getUsername(), roles);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Invalid email or password.");
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: User not authenticated.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userDetails);
    }
}

package com.shrewd.service;

import com.shrewd.config.DataSourceService;
import com.shrewd.model.orgs.Organization;
import com.shrewd.repository.orgs.OrganizationRepository;
import com.shrewd.security.communication.request.OrgRegisterRequest;
import org.flywaydb.core.Flyway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CreatingOrganization {

    private final OrganizationRepository organizationRepository;
    private final DataSourceService dataSourceService;
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private final Flyway flyway;

    public CreatingOrganization(OrganizationRepository organizationRepository, DataSourceService dataSourceService, JdbcTemplate jdbcTemplate, Flyway flyway) {
        this.organizationRepository = organizationRepository;
        this.dataSourceService = dataSourceService;
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = new BCryptPasswordEncoder(12);
        this.flyway = flyway;
    }

    public ResponseEntity<?> createOrganization(OrgRegisterRequest registerRequest) {
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
            flyway.migrate();
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

}

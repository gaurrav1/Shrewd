package com.shrewd.service.implemantation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String createDatabaseForTenant(String tenantId) {
        if (!isValidTenantName(tenantId)) {
            return "Error: Invalid tenant name.";
        }

        String createDatabaseQuery = String.format("CREATE DATABASE IF NOT EXISTS org_%s", tenantId);

        try {
            jdbcTemplate.execute(createDatabaseQuery);
            // https://www.digitalocean.com/community/tutorials/sql-injection-in-java
            return "Database created successfully.";
        } catch (Exception e) {
            // Log the exception (use a proper logger instead of System.out.println)
            return "Error: Failed to create tenant database.";
        }
    }

    private boolean isValidTenantName(String tenantId) {
        return tenantId != null && tenantId.matches("^[a-zA-Z0-9_-]+$");
    }

    public void deleteDatabaseForTenant(String tenantId) {
        if (!isValidTenantName(tenantId)) {
            throw new IllegalArgumentException("Invalid tenant name: " + tenantId);
        }

        String dropDatabaseQuery = String.format("DROP DATABASE IF EXISTS org_%s", tenantId);

        try {
            jdbcTemplate.execute(dropDatabaseQuery);
            System.out.println("Database org_" + tenantId + " deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting database for tenant: " + tenantId);
            e.printStackTrace();
        }
    }
}

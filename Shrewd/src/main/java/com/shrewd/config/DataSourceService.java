package com.shrewd.config;

import com.shrewd.config.hibernate.TenantContext;
import com.shrewd.security.communication.request.RegisterRequest;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;

@Service
public class DataSourceService {
    private final DynamicDataSource dynamicDataSource;

    public DataSourceService(DynamicDataSource dynamicDataSource) {
        this.dynamicDataSource = dynamicDataSource;
    }

    public void registerTenantDataSource(String tenantId) {
        String dbName = "org_" + tenantId;
        DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/" + dbName)
                .username("gaurav")
                .password("NGaurav@113")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();

        dynamicDataSource.addTargetDataSource(tenantId, dataSource);
    }

    public void registerTenantDataSource(String tenantId, RegisterRequest registerRequest) {
        String dbName = "org_" + tenantId;
        DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/" + dbName)
                .username("gaurav")
                .password("NGaurav@113")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();

        dynamicDataSource.addTargetDataSource(tenantId, dataSource);

        TenantContext.setCurrentTenant(tenantId);
        initializeDatabase(new JdbcTemplate(dataSource), registerRequest);
        TenantContext.clear();
    }

    private void initializeDatabase(JdbcTemplate jdbcTemplate, RegisterRequest registerRequest) {
        try {
            // Create 'roles' table
            String createRolesTable = "CREATE TABLE IF NOT EXISTS roles ("
                    + "role_id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "role_name VARCHAR(20) NOT NULL) ";

            // Create 'employee' table
            String createEmployeeTable = "CREATE TABLE IF NOT EXISTS employee ("
                    + "user_id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "username VARCHAR(50) NOT NULL, "
                    + "email VARCHAR(100) NOT NULL, "
                    + "name VARCHAR(100), "
                    + "phone VARCHAR(20), "
                    + "password VARCHAR(120) NOT NULL, "
                    + "role_id INT NOT NULL, "
                    + "account_non_locked BOOLEAN NOT NULL, "
                    + "account_non_expired BOOLEAN NOT NULL, "
                    + "credentials_non_expired BOOLEAN NOT NULL, "
                    + "enabled BOOLEAN NOT NULL, "
                    + "credentials_expiry_date DATE, "
                    + "account_expiry_date DATE, "
                    + "created_date DATETIME DEFAULT CURRENT_TIMESTAMP, "
                    + "updated_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, "
                    + "FOREIGN KEY (role_id) REFERENCES roles(role_id))";

            // Insert default roles (if not exists)
            String insertRoles = "INSERT INTO roles (role_name) VALUES "
                    + "('ORGANIZATION'), "
                    + "('ADMIN'), "
                    + "('HR'), "
                    + "('MANAGER'), "
                    + "('EMPLOYEE') "
                    + "ON DUPLICATE KEY UPDATE role_name = role_name";  // Prevent duplicates

            // Execute queries
            jdbcTemplate.execute(createRolesTable);
            jdbcTemplate.execute(createEmployeeTable);
            jdbcTemplate.update(insertRoles);

            String insertEmployee = "INSERT INTO employee (username, email, name, phone, password, role_id, "
                    + "account_non_locked, account_non_expired, credentials_non_expired, enabled, "
                    + "credentials_expiry_date, account_expiry_date) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            jdbcTemplate.update(insertEmployee,
                    registerRequest.getUsername(),
                    registerRequest.getEmail(),
                    registerRequest.getName(),
                    registerRequest.getPhone(),
                    registerRequest.getPassword(),
                    1,
                    true, // account_non_locked
                    true, // account_non_expired
                    true, // credentials_non_expired
                    true, // enabled
                    null, // credentials_expiry_date (can be null or some date)
                    null  // account_expiry_date (can be null or some date)
            );


            System.out.println("Database schema initialized successfully.");

        } catch (Exception e) {
            throw new RuntimeException("Error initializing tenant database: " + e.getMessage(), e);
        }
    }
}

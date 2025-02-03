package com.shrewd.config;

import com.shrewd.repository.orgs.OrganizationRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultitenantConfiguration {

    private final String DB_URL = "jdbc:mysql://localhost:3306/";
    private final String DB_USERNAME = "gaurav";
    private final String DB_PASSWORD = "NGaurav@113";

    @Primary
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "tenants")
    public DataSource dataSource() {
        Map<Object, Object> resolvedDataSources = new HashMap<>();

        DataSource defaultDataSource = createDataSource("shrewd");
        resolvedDataSources.put("etrhefhredtghbrtghbrtghrtdgh", defaultDataSource);

        loadExistingTenants(resolvedDataSources);

        AbstractRoutingDataSource dataSource = new DynamicDataSource();
        String defaultTenant = "etrhefhredtghbrtghbrtghrtdgh";
        dataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));
        dataSource.setTargetDataSources(resolvedDataSources);

        dataSource.afterPropertiesSet();
        return dataSource;
    }

    private void loadExistingTenants(Map<Object, Object> resolvedDataSources) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             Statement stmt = connection.createStatement()) {
            var rs = stmt.executeQuery("SHOW DATABASES LIKE 'org_%'"); // Finds all tenant databases

            while (rs.next()) {
                String tenantDbName = rs.getString(1); // Database name
                String trimmedTenantDbName = tenantDbName.length() > 4 ? tenantDbName.substring(4) : tenantDbName;
                System.out.println("Found tenant database: " + tenantDbName);
                resolvedDataSources.put(trimmedTenantDbName, createDataSource(tenantDbName));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error loading tenant databases", e);
        }
    }

    private DataSource createDataSource(String dbName) {
        String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
        return DataSourceBuilder.create()
                .url(DB_URL + dbName)
                .username(DB_USERNAME)
                .password(DB_PASSWORD)
                .driverClassName(DRIVER_CLASS)
                .build();
    }

}

package com.shrewd.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class MultitenantConfiguration {

    @Value("${spring.datasource.base-url}")
    private String DB_URL;

    @Value("${spring.datasource.username}")
    private String DB_USERNAME;

    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;

    @Primary
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "tenants")
    public DataSource dataSource() {
        Map<Object, Object> resolvedDataSources = new ConcurrentHashMap<>();

        HikariDataSource defaultDataSource = createDataSource("shrewd");
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

    private HikariDataSource createDataSource(String dbName) {
        HikariConfig config = new HikariConfig();
        String jdbcUrl = DB_URL + dbName;
        System.out.println("Creating datasource for: " + jdbcUrl);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(DB_USERNAME);
        config.setPassword(DB_PASSWORD);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);
        return new HikariDataSource(config);
    }

}

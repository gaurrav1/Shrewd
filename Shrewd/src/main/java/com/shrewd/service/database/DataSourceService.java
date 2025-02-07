package com.shrewd.service.database;

import com.shrewd.config.DynamicDataSource;
import com.shrewd.security.communication.request.RegisterRequest;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;

@Service
public class DataSourceService {

    private final DatabaseService databaseService;
    @Value("${spring.datasource.base-url}")
    private String DB_URL;

    @Value("${spring.datasource.username}")
    private String DB_USERNAME;

    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;

    private final DynamicDataSource dynamicDataSource;


    public DataSourceService(DynamicDataSource dynamicDataSource, DatabaseService databaseService) {
        this.dynamicDataSource = dynamicDataSource;
        this.databaseService = databaseService;
    }

    public void registerTenantDataSource(String tenantId, RegisterRequest registerRequest) {
        String jdbcUrl = DB_URL + "org_" + tenantId;

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(DB_USERNAME);
        hikariConfig.setPassword(DB_PASSWORD);
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setConnectionTimeout(30000);

        DataSource dataSource = new HikariDataSource(hikariConfig);

        dynamicDataSource.addTargetDataSource(tenantId, dataSource);

        databaseService.initializeDatabase(new JdbcTemplate(dataSource), registerRequest);
    }

}

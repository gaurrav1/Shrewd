package com.shrewd.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;

@Service
public class DataSourceService {
    private final DynamicDataSource dynamicDataSource;

    public DataSourceService(DynamicDataSource dynamicDataSource) {
        this.dynamicDataSource = dynamicDataSource;
    }

    public void registerTenantDataSource(String tenantId) {
        DataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/org_" + tenantId)
                .username("gaurav")
                .password("NGaurav@113")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();

        dynamicDataSource.addTargetDataSource(tenantId, dataSource);

        try{
            Flyway flyway = Flyway.configure()
                    .dataSource(dataSource)
                    .load();
            flyway.migrate();
        } catch (Exception e) {
            throw new RuntimeException("Error while migrating schema");
        }
    }
}
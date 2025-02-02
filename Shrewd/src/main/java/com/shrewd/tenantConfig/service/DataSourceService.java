package com.shrewd.tenantConfig.service;

import com.shrewd.tenantConfig.DataSourceRegistry;
import com.shrewd.tenantConfig.DynamicDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;

//@Service
//public class DataSourceService {
//
//    public void createAndRegisterTenantDataSource(String tenantId, String username, String password) {
//        String tenantDbUrl = "jdbc:mysql://localhost:3306/org_" + tenantId;
//
//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl(tenantDbUrl);
//        config.setUsername(username);
//        config.setPassword(password);
//        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        config.setMaximumPoolSize(5);
//
//        DataSource dataSource = new HikariDataSource(config);
//        DataSourceRegistry.registerDataSource(tenantId, dataSource);
//
//        Flyway flyway = Flyway.configure()
//                .dataSource(dataSource)
//                .load();
//        flyway.migrate();
//    }
//
//}
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
    }
}
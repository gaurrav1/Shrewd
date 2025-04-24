package com.shrewd.config.hibernate;

import com.shrewd.config.MultiTenantRoutingDataSource;
import com.shrewd.config.tenant.TenantDataSourceProvider;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.shrewd.model.users",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class DataSourceConfig {

    @Bean(name = "customDataSource")
    public DataSource dataSource(TenantDataSourceProvider provider) {
        MultiTenantRoutingDataSource routingDataSource = new MultiTenantRoutingDataSource(provider);
        routingDataSource.setTargetDataSources(new HashMap<>());
        return routingDataSource;
    }


    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("customDataSource") DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPackagesToScan("com.shrewd.model.users");
        factory.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect");

        factory.setJpaPropertyMap(jpaProperties);
        return factory;
    }


//    @Bean(name = "entityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("customDataSource") DataSource dataSource) {
//        var factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setPackagesToScan("com.shrewd.model.users");
//        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
//        Map<String, Object> jpaProperties = new HashMap<>();
//        jpaProperties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQLDialect"); // or PostgreSQLDialect if needed
//
//        factory.setJpaPropertyMap(jpaProperties);
//        return factory;
//    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}

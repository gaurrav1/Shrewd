package com.shrewd.config.hibernate;

import org.hibernate.cfg.Environment;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class HibernateMultiTenancyConfig {

    private final JpaProperties jpaProperties;

    public HibernateMultiTenancyConfig(JpaProperties jpaProperties) {
        this.jpaProperties = jpaProperties;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource masterDataSource,
            TenantConnectionProvider tenantConnectionProvider,
            TenantIdentifierResolver tenantIdentifierResolver) {

        Map<String, Object> jpaPropertiesMap = new HashMap<>(jpaProperties.getProperties());

        jpaPropertiesMap.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, tenantConnectionProvider);
        jpaPropertiesMap.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);
        jpaPropertiesMap.put(Environment.FORMAT_SQL, true);
        jpaPropertiesMap.put("hibernate.hbm2ddl.auto", "none");
        jpaPropertiesMap.put("hibernate.show_sql", true);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(masterDataSource);
        em.setPackagesToScan("com.shrewd.model");
        em.setJpaVendorAdapter(this.jpaVendorAdapter());
        em.setJpaPropertyMap(jpaPropertiesMap);

        return em;
    }
}

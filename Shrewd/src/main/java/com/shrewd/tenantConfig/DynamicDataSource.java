package com.shrewd.tenantConfig;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DynamicDataSource extends AbstractRoutingDataSource {
    private final Map<Object, Object> targetDataSources = new HashMap<>();

    public DynamicDataSource() {
        super.setTargetDataSources(targetDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantContext.getCurrentTenant();
    }

    // Add this method to populate target data sources
    public void addTargetDataSource(String tenantId, DataSource dataSource) {
        this.targetDataSources.put(tenantId, dataSource);
        super.afterPropertiesSet(); // Refresh the data source map
    }
}
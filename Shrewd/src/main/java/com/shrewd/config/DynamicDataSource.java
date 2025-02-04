package com.shrewd.config;

import com.shrewd.config.hibernate.TenantContext;
import com.shrewd.config.hibernate.TenantIdentifierResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Autowired
	TenantIdentifierResolver tenantIdentifierResolver;

	private final Map<Object, Object> targetDataSources = new ConcurrentHashMap<>();


	public DynamicDataSource() {
		super.setTargetDataSources(targetDataSources);
	}

	@Override
    public Object determineCurrentLookupKey() {
		System.out.println("Tenant ID: " + TenantContext.getCurrentTenant());
		return tenantIdentifierResolver.resolveCurrentTenantIdentifier();
	}

	public void addTargetDataSource(String tenantId, DataSource dataSource) {
		targetDataSources.put(tenantId, dataSource);
		super.setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}
}

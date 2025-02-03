package com.shrewd.config;

import com.shrewd.config.hibernate.TenantContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DynamicDataSource extends AbstractRoutingDataSource {

	private final Map<Object, Object> targetDataSources = new ConcurrentHashMap<>();

	public DynamicDataSource() {
		super.setTargetDataSources(targetDataSources);
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return TenantContext.getCurrentTenant();
	}

	public void addTargetDataSource(String tenantId, DataSource dataSource) {
		targetDataSources.put(tenantId, dataSource);
		super.setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}
}

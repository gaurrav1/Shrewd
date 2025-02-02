package com.shrewd.tenantConfig;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataSourceRegistry {

    private static final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    public static void registerDataSource(String tenantId, DataSource dataSource) {
        dataSourceMap.put(tenantId, dataSource);
    }

    public static DataSource getDataSource(String tenantId) {
        return dataSourceMap.get(tenantId);
    }
}

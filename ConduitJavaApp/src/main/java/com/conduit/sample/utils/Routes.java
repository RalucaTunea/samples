package com.conduit.sample.utils;

import com.conduit.sample.config.AppConfig;

public class Routes {
    public static String environment = AppConfig.getConfig("ENVIRONMENT");
    public static String metadataAll = environment + "/query/metadata/all";
    public static String auth = environment + "/auth";
    public static String metadataDatasource = environment + "/api/metadata/datasources";
    public static String metadataDatasourceNames = environment + "/api/metadata/datasourcesNames";
    public static String metadataExploreOracle = environment + "/api/metadata/explore/oracle";
    public static String queryExecute = environment + "/query/execute";
    public static String databaseUrl = AppConfig.getConfig("DBURL");
}

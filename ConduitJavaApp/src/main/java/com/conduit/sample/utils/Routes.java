package com.conduit.sample.utils;

import com.conduit.sample.config.AppConfig;

public class Routes {
    public static String environment = AppConfig.getConfig("URL");
    public static String metadataAll = environment + "/api/metadata/all";
    public static String auth = environment + "/auth";
    public static String metadataDatasource = environment + "/api/metadata/datasources";
    public static String metadataDatasourceNames = environment + "/api/metadata/datasourcesNames";
    public static String metadataExploreOracle = environment + "/api/metadata/explore/oracle";
    public static String queryExecute = environment + "/api/query/execute";
    public static String databaseUrl = AppConfig.getConfig("JDBC_URL");
}

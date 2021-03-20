package com.conduit.sample.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class AppConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    private String email;
    private String password;
    private String tokenUser;

    private String authenticationType;
    private String dataSourceLocation;
    private String connectionUsername;
    private String description;
    private String connectorName;

    private String typeName;
    private List<String> tables;
    private int partitionColumnsCount;
    private Boolean isAuthorizationEnabled;

    private String userJDBC;
    private String passJDBC;
    private String jdbcUrl;

    private String url;


    public AppConfig() {
        this.email = AppConfig.getConfig("EMAIL");
        this.password = AppConfig.getConfig("PASSWORD");
        this.tokenUser = AppConfig.getConfig("TOKEN");
        this.authenticationType = AppConfig.getConfig("AUTHENTICATION_TYPE");
        this.dataSourceLocation = AppConfig.getConfig("DATA_SOURCE_LOCATION");
        this.connectionUsername = AppConfig.getConfig("USERNAME");
        this.description = AppConfig.getConfig("DESCRIPTION");
        this.connectorName = AppConfig.getConfig("CONNECTOR_NAME");
        this.typeName = AppConfig.getConfig("TYPE_DATABASE");
        String stringTables = AppConfig.getConfig("TABLES");
        this.tables = Arrays.asList(stringTables.split(","));
        this.partitionColumnsCount = Integer.parseInt(AppConfig.getConfig("PARTITION_COLUMNS_COUNT"));
        this.isAuthorizationEnabled = Boolean.parseBoolean(AppConfig.getConfig("IS_AUTHORIZATION"));
        this.userJDBC = AppConfig.getConfig("USER_JDBC");
        this.passJDBC = AppConfig.getConfig("PASSWORD_JDBC");
        this.url = AppConfig.getConfig("URL");
        this.jdbcUrl = AppConfig.getConfig("JDBC_URL");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public String getDataSourceLocation() {
        return dataSourceLocation;
    }

    public String getConnectionUsername() {
        return connectionUsername;
    }

    public String getDescription() {
        return description;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<String> getTables() {
        return tables;
    }

    public int getPartitionColumnsCount() {
        return partitionColumnsCount;
    }

    public Boolean getAuthorizationEnabled() {
        return isAuthorizationEnabled;
    }

    public String getTokenUser() {
        return tokenUser;
    }

    public String getUserSQL() {
        return userJDBC;
    }

    public String getPassSQL() {
        return passJDBC;
    }

    public String getUrl() { return url;}

    public String getJdbcUrl() { return jdbcUrl; }

    public static String getConfig(String config) {
        String configValue = System.getProperty(config);
        if (configValue.isEmpty() && !config.equals("TOKEN")) {
            LOGGER.warn(config + " is null. Please insert it.");
        }
        return configValue;
    }
}

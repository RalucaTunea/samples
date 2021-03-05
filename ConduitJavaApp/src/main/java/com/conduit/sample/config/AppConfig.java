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
    private String connectionUrl;
    private String connectionUsername;
    private String description;
    private String connectorName;
    private String namespace;
    private String subscriptionId;

    private String typeName;
    private List<String> tables;
    private int specificColumns;
    private Boolean isAuthorizationEnabled;
    private String userSubscription;

    private String userSQL;
    private String passSQL;
    private String dburl;

    private String environment;


    public AppConfig() {
        this.email = AppConfig.getConfig("EMAIL");
        this.password = AppConfig.getConfig("PASSWORD");
        this.tokenUser = AppConfig.getConfig("TOKEN");
        this.authenticationType = AppConfig.getConfig("AUTHENTICATION");
        this.connectionUrl = AppConfig.getConfig("URL");
        this.connectionUsername = AppConfig.getConfig("USERNAME");
        this.description = AppConfig.getConfig("DESCRIPTION");
        this.connectorName = AppConfig.getConfig("CONNECTOR_NAME");
        this.namespace = AppConfig.getConfig("NAMESPACE");
        this.subscriptionId = "";
        this.typeName = AppConfig.getConfig("TYPE_DATABASE");
        String stringTables = AppConfig.getConfig("TABLES");
        this.tables = Arrays.asList(stringTables.split(","));
        this.specificColumns = Integer.parseInt(AppConfig.getConfig("SPECIFIC_COLUMNS"));
        this.isAuthorizationEnabled = Boolean.parseBoolean(AppConfig.getConfig("IS_AUTHORIZATION"));
        this.userSubscription = "";
        this.userSQL = AppConfig.getConfig("USERSQL");
        this.passSQL = AppConfig.getConfig("PASSSQL");
        this.environment = AppConfig.getConfig("ENVIRONMENT");
        this.dburl = AppConfig.getConfig("DBURL");
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

    public String getConnectionUrl() {
        return connectionUrl;
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

    public String getNamespace() {
        return namespace;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<String> getTables() {
        return tables;
    }

    public int getSpecificColumns() {
        return specificColumns;
    }

    public Boolean getAuthorizationEnabled() {
        return isAuthorizationEnabled;
    }

    public String getUserSubscription() {
        return userSubscription;
    }

    public String getTokenUser() {
        return tokenUser;
    }

    public String getUserSQL() {
        return userSQL;
    }

    public String getPassSQL() {
        return passSQL;
    }

    public String getEnvironment() { return environment;}

    public String getDburl() { return dburl; }

    public static String getConfig(String config) {
        String configValue = System.getProperty(config);
        if (configValue.isEmpty() && !config.equals("TOKEN")) {
            LOGGER.warn(config + " is null. Please insert it.");
        }
        return configValue;
    }
}

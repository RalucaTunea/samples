package com.conduit.sample.services.entity;

import org.apache.commons.codec.binary.Base64;

import java.util.*;

public class Connector {
    private String name;
    private String namespace = "default";
    private String database;
    private String connectionUrl;
    private String connectionUsername;
    private String connectionPassword;
    private String description = "";
    private String typeName;
    private List<String> tableNames = new ArrayList<>();
    private String authenticationType;
    private String cluster = "local";
    private Map<String, List<List<String>>> tablesSchema = new HashMap<>();
    private Map<String, String> tablesCache = new HashMap<>();
    private Map<String, Boolean> tablesEagerCache = new HashMap<>();
    private Map<String, Boolean> tablesAutoRefresh = new HashMap<>();
    private Map<String, String> tablesAlias = new HashMap<>();
    private Map<String, String> tablesDescription = new HashMap<>();
    private Map<String, String> tablesTags = new HashMap<>();
    private Map<String, String> storageLevel = new HashMap<>();
    private String userSubscription = "";
    private String serviceSubscription = "";
    private Boolean isAuthorizationEnabled = false;
    private Boolean enableQueryCache = false;
    private Boolean connectorCacheEnabled = false;
    private Boolean shouldUpdateTree = false;
    private Map<String, Map<String, Object>> specificColumns = new HashMap<>();
    private Map<String, String> fetchSize = Collections.emptyMap();
    private Map<String, String> partitionSize = Collections.emptyMap();
    private Map<String, String> queryTimeout = Collections.emptyMap();
    private Map<String, String> adSubscriptions = Collections.emptyMap();
    private List<Map<String, String>> authenticationRadios = new ArrayList<>();
    private Map<String, Object> parquetPartitionColumns = new HashMap<>();
    private String displayName = "Oracle";
    private Map<String, Object> databaseValue = new HashMap<>();
    private String selection = null;
    private Map<String, String> dataSourceConnection = new HashMap<>();

    public Map<String, Object> getParquetPartitionColumns() {
        return parquetPartitionColumns;
    }

    public void setParquetPartitionColumns(Map<String, Object> parquetPartitionColumns) {
        this.parquetPartitionColumns = parquetPartitionColumns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getConnectionUsername() {
        return connectionUsername;
    }

    public void setConnectionUsername(String connectionUsername) {
        this.connectionUsername = connectionUsername;
    }

    public String getConnectionPassword() {
        return connectionPassword;
    }

    public void setConnectionPassword(String connectionPassword) {
        this.connectionPassword = connectionPassword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<String> getTableNames() {
        return tableNames;
    }

    public void setTableNames(List<String> tableNames) {
        this.tableNames = tableNames;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }


    public Map<String, String> getStorageLevel() {
        return storageLevel;
    }

    public void setStorageLevel() {
        for (String name : this.tableNames)
            storageLevel.put(name, "DISK_ONLY");

    }

    public String getUserSubscription() {
        return userSubscription;
    }

    public void setUserSubscription(String userSubscription) {
        this.userSubscription = userSubscription;
    }

    public String getServiceSubscription() {
        return serviceSubscription;
    }

    public void setServiceSubscription(String serviceSubscription) {
        this.serviceSubscription = serviceSubscription;
    }

    public Boolean getIsAuthorizationEnabled() {
        return isAuthorizationEnabled;
    }

    public void setIsAuthorizationEnabled(Boolean isauthorizationEnabled) {
        this.isAuthorizationEnabled = isauthorizationEnabled;
    }

    public Boolean getEnableQueryCache() {
        return enableQueryCache;
    }

    public void setEnableQueryCache(Boolean enableQueryCache) {
        this.enableQueryCache = enableQueryCache;
    }

    public Boolean getConnectorCacheEnabled() {
        return connectorCacheEnabled;
    }

    public void setConnectorCacheEnabled(Boolean connectorCacheEnabled) {
        this.connectorCacheEnabled = connectorCacheEnabled;
    }

    public Boolean getShouldUpdateTree() {
        return shouldUpdateTree;
    }

    public void setShouldUpdateTree(Boolean shouldUpdateTree) {
        this.shouldUpdateTree = shouldUpdateTree;
    }

    public Map<String, Map<String, Object>> getSpecificColumns() {
        return specificColumns;
    }

    public void setSpecificColumns(int partitionCount, List<Map<String, Object>> tables) {
        Map<String, Object> storageLevelObject = new HashMap<String, Object>();
        for (Map.Entry<String, String> entry : storageLevel.entrySet()) {
            if (entry.getValue() instanceof String) {
                storageLevelObject.put(entry.getKey(), entry.getValue());
            }
        }
        specificColumns.put("storageLevel", storageLevelObject);
        Map<String, Object> partitionColumn = new HashMap<>();
        Map<String, Object> partitionCountTable = new HashMap<>();
        Map<String, Object> partitionColumnsTables = new HashMap<>();

        for (String name : this.tableNames) {
            partitionColumn.put(name, "");
            partitionCountTable.put(name, partitionCount);
            for (Map<String, Object> o : tables) {
                String tableNameResponse = o.get("name").toString();
                List<List<String>> partitionColumnsList = (List<List<String>>) o.get("partitionColumns");
                if (tableNameResponse.equals(name))
                    partitionColumnsTables.put(name, partitionColumnsList);
            }
        }
        specificColumns.put("partitionColumn", partitionColumn);
        specificColumns.put("partitionCount", partitionCountTable);
        specificColumns.put("partitionColumnsList", partitionColumnsTables);
    }


    public Map<String, List<List<String>>> getTablesSchema() {
        return tablesSchema;
    }

    public void setTablesSchema(List<Map<String, Object>> tables) {

        if (!tableNames.isEmpty()) {
            for (String name : tableNames)
                for (Map<String, Object> o : tables) {
                    String tablesNameResponse = o.get("name").toString();
                    List<List<String>> listColumns = (List<List<String>>) o.get("columns");
                    if (tablesNameResponse.equals(name))
                        tablesSchema.put(tablesNameResponse, listColumns);
                }
        }
    }

    public Map<String, String> getTablesCache() {
        return tablesCache;
    }

    public void setTablesCache(Map<String, String> tablesCache) {
        this.tablesCache = tablesCache;
    }

    public Map<String, Boolean> getTablesEagerCache() {
        return tablesEagerCache;
    }

    public void setTablesEagerCache(Map<String, Boolean> tablesEagerCache) {
        this.tablesEagerCache = tablesEagerCache;
    }

    public Map<String, Boolean> getTablesAutoRefresh() {
        return tablesAutoRefresh;
    }

    public void setTablesAutoRefresh(Map<String, Boolean> tablesAutoRefresh) {
        this.tablesAutoRefresh = tablesAutoRefresh;
    }

    public Map<String, String> getTablesAlias() {
        return tablesAlias;
    }

    public void setTablesAlias(Map<String, String> tablesAlias) {
        this.tablesAlias = tablesAlias;
    }

    public Map<String, String> getTablesDescription() {
        return tablesDescription;
    }

    public void setTablesDescription(Map<String, String> tablesDescription) {
        this.tablesDescription = tablesDescription;
    }

    public Map<String, String> getTablesTags() {
        return tablesTags;
    }

    public void setTablesTags(Map<String, String> tablesTags) {
        this.tablesTags = tablesTags;
    }

    public Map<String, String> getFetchSize() {
        return fetchSize;
    }

    public void setFetchSize(Map<String, String> fetchSize) {
        this.fetchSize = fetchSize;
    }

    public Map<String, String> getPartitionSize() {
        return partitionSize;
    }

    public void setPartitionSize(Map<String, String> partitionSize) {
        this.partitionSize = partitionSize;
    }

    public Map<String, String> getQueryTimeout() {
        return queryTimeout;
    }

    public void setQueryTimeout(Map<String, String> queryTimeout) {
        this.queryTimeout = queryTimeout;
    }

    public Map<String, String> getAdSubscriptions() {
        return adSubscriptions;
    }

    public void setAdSubscriptions() {
        if (userSubscription != "")
            adSubscriptions.put("user_subscription", userSubscription);
    }

    public List<Map<String, String>> getAuthenticationRadios() {
        return authenticationRadios;
    }

    public void setAuthenticationRadios() {
        Map<String, String> anonymous_with_impersonation = new HashMap<>();
        Map<String, String> basic_with_impersonation = new HashMap<>();
        Map<String, String> active_directory_with_impersonation = new HashMap<>();
        Map<String, String> basic_pass_through = new HashMap<>();

        anonymous_with_impersonation.put("realValue", "anonymous_with_impersonation");
        anonymous_with_impersonation.put("displayValue", "Anonymous with Impersonation");

        basic_with_impersonation.put("realValue", "basic_with_impersonation");
        basic_with_impersonation.put("displayValue", "Conduit Authentication with Impersonation");

        active_directory_with_impersonation.put("realValue", "active_directory_with_impersonation");
        active_directory_with_impersonation.put("displayValue", "Active Directory with Impersonation");

        basic_pass_through.put("realValue", "basic_pass_through");
        basic_pass_through.put("displayValue", "User Credentials Pass Through");

        authenticationRadios.add(anonymous_with_impersonation);
        authenticationRadios.add(basic_with_impersonation);
        authenticationRadios.add(active_directory_with_impersonation);
        authenticationRadios.add(basic_pass_through);

    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Map<String, Object> getDatabaseValue() {
        return databaseValue;
    }

    public void setDatabaseValue() {
        databaseValue.put("value", true);
        databaseValue.put("validators", Collections.EMPTY_LIST);
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public Map<String, String> getDataSourceConnection() {
        byte[] encodedBytes = Base64.encodeBase64(this.connectionPassword.getBytes());
        dataSourceConnection.put("database", database);
        dataSourceConnection.put("connectionUrl", connectionUrl);
        dataSourceConnection.put("connectionUsername", connectionUsername);
        dataSourceConnection.put("connectionPassword", new String(encodedBytes));
        return dataSourceConnection;
    }

    public void setDataSourceConnection() {
        byte[] encodedBytes = Base64.encodeBase64(connectionPassword.getBytes());
        dataSourceConnection.put("database", database);
        dataSourceConnection.put("connectionUrl", connectionUrl);
        dataSourceConnection.put("connectionUsername", connectionUsername);
        dataSourceConnection.put("connectionPassword", new String(encodedBytes));
    }

    @Override
    public String toString() {
        return "Connector{" +
                "name='" + name + '\'' +
                ", namespace='" + namespace + '\'' +
                ", database='" + database + '\'' +
                ", connectionUrl='" + connectionUrl + '\'' +
                ", connectionUsername='" + connectionUsername + '\'' +
                ", connectionPassword='" + connectionPassword + '\'' +
                ", description='" + description + '\'' +
                ", typeName='" + typeName + '\'' +
                ", tableNames=" + tableNames +
                ", authenticationType='" + authenticationType + '\'' +
                ", cluster='" + cluster + '\'' +
                ", tablesSchema=" + tablesSchema +
                ", tablesCache=" + tablesCache +
                ", tablesEagerCache=" + tablesEagerCache +
                ", tablesAutoRefresh=" + tablesAutoRefresh +
                ", tablesAlias=" + tablesAlias +
                ", tablesDescription=" + tablesDescription +
                ", tablesTags=" + tablesTags +
                ", storageLevel=" + storageLevel +
                ", userSubscription='" + userSubscription + '\'' +
                ", serviceSubscription='" + serviceSubscription + '\'' +
                ", isAuthorizationEnabled=" + isAuthorizationEnabled +
                ", enableQueryCache=" + enableQueryCache +
                ", connectorCacheEnabled=" + connectorCacheEnabled +
                ", shouldUpdateTree=" + shouldUpdateTree +
                ", specificColumns=" + specificColumns +
                ", fetchSize=" + fetchSize +
                ", partitionSize=" + partitionSize +
                ", queryTimeout=" + queryTimeout +
                ", adSubscriptions=" + adSubscriptions +
                ", authenticationRadios=" + authenticationRadios +
                ", displayName='" + displayName + '\'' +
                ", databaseValue=" + databaseValue +
                ", selection='" + selection + '\'' +
                ", dataSourceConnection=" + dataSourceConnection +
                ", parquetPartitionColumns=" + parquetPartitionColumns +
                '}';
    }
}

using System;
using System.Collections.Generic;

using NLog;

namespace config
{
    public class AppConfig
    {
        private static Logger logger = LogManager.GetCurrentClassLogger();
        private string email;
        private string password;
        private string authenticationType;
        private string connectionUrl;
        private string connectionUsername;
        private string subcriptionId;
        private string typeName;
        private List<string> tables;
        private int partitionColumnsCount;
        private Boolean isAuthorizationEnabled;
        private string tableName;
        private string connectorName;

        public AppConfig()
        {
            email = getConfig("EMAIL");
            password = getConfig("PASSWORD");
            authenticationType = getConfig("AUTHENTICATION_TYPE");
            connectionUrl = getConfig("CONNECTION_URL");
            connectionUsername = getConfig("CONNECTION_USERNAME");
            subcriptionId = getConfig("SUBSCRIPTION_ID");
            typeName = getConfig("TYPE_NAME");
            tables = new List<string>(getConfig("TABLES").Split(','));
            partitionColumnsCount = Int32.Parse(getConfig("PARTITION_COUNT"));
            isAuthorizationEnabled = Boolean.Parse(getConfig("IS_AUTHORIZATION_ENABLED"));
            tableName = getConfig("TABLE_NAME");
            connectorName = getConfig("CONNECTOR_NAME");
        }

        public string getEmail(){
            return email;
        }

        public string getPassword(){
            return password;
        }

        public string getAuthenicationType(){
            return authenticationType;
        }

        public string getConnectionUrl(){
            return connectionUrl;
        }

        public string getConnectionUsername(){
            return connectionUsername;
        }

        public string getSubsriptionId(){
            return subcriptionId;
        }

        public string getTypeName(){
            return typeName;
        }

        public List<string>  getTables(){
            return tables;
        }

        public int getPartitionCount(){
            return partitionColumnsCount;
        }

        public Boolean getIsAuthorizationEnabled(){
            return isAuthorizationEnabled;
        }

        public string getTableName(){
            return tableName;
        }

        public string getConnectorName(){
            return connectorName;
        }

        public static String getConfig(String config)
        {
            String configValue = Environment.GetEnvironmentVariable(config);
            if (configValue == null)
            {
                logger.Error(config + "is null. Please insert it.");
                Environment.Exit(1);
            }
            return configValue;
        }
    }

}
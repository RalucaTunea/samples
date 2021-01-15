using System;
using System.Text;
using System.Net.Http;
using System.Collections.Generic;

using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

using response_interface = response.IResponse;
using db_response = response.DBResponse;
using const_value = consts.Consts;

namespace request
{
    public class CreateConnectorRequest : IRequest
    {

        public string name { get; set; }
        public string namespaceValue { get; set; } = "default";
        public string database { get; set; }
        public string connectionUrl { get; set; }
        public string connectionUsername { get; set; }
        public string connectionPassword { get; set; }
        public string description { get; set; } = "";
        public string typeName { get; set; }
        public List<string> tableNames { get; set; }
        public string authenticationType { get; set; }
        public string cluster { get; set; } = "local";
        public Dictionary<string, JArray> tablesSchema { get; } = new Dictionary<string, JArray>();
        public Dictionary<string, string> tablesCache { get; set; } = new Dictionary<string, string>();
        public Dictionary<string, Boolean> tablesEagerCache { get; set; } = new Dictionary<string, Boolean>();
        public Dictionary<string, Boolean> tablesAutoRefresh { get; set; } = new Dictionary<string, Boolean>();
        public Dictionary<string, string> tablesAlias { get; set; } = new Dictionary<string, string>();
        public Dictionary<string, string> tablesDescription { get; set; } = new Dictionary<string, string>();
        public Dictionary<string, string> tablesTags { get; set; } = new Dictionary<string, string>();
        public Dictionary<string, string> storageLevel { get; set; } = new Dictionary<string, string>();
        public string userSubscription { get; set; } = "";
        public string serviceSubscription { get; set; } = "";
        public Boolean isAuthorizationEnabled { get; set; } = false;
        public Boolean enableQueryCache { get; set; } = false;
        public Boolean connectorCacheEnabled { get; set; } = false;
        public Boolean shouldUpdateTree { get; set; } = false;
        public Dictionary<string, Object> specificColumns { get; set; } = new Dictionary<string, Object>();
        public Dictionary<string, string> fetchSize { get; set; } = new Dictionary<string, string>();
        public Dictionary<string, string> partitionSize { get; set; } = new Dictionary<string, string>();
        public Dictionary<string, string> queryTimeout { get; set; } = new Dictionary<string, string>();
        public Dictionary<string, string> adSubscriptions { get; set; } = new Dictionary<string, string>();
        public List<Dictionary<string, string>> authenticationRadios { get; set; } = new List<Dictionary<string, string>>();
        public string displayName { get; set; } = "Oracle";
        public Dictionary<string, Object> databaseValue { get; set; } = new Dictionary<string, Object>();
        public string selection { get; set; } = null;
        public Dictionary<string, string> dataSourceConnection { get; set; } = new Dictionary<string, string>();

        public StringContent payload { get; set; }
        public StringContent createPayload()
        {
            dynamic json = new JObject();
            json.name = name;
            json.Add("namespace", namespaceValue);
            json.database = database;
            json.connectionUrl = connectionUrl;
            json.connectionUsername = connectionUsername;
            json.connectionPassword = connectionPassword;
            json.description = description;
            json.typeName = typeName;
            json.tableNames = new JArray(tableNames);
            json.authenticationType = authenticationType;
            json.cluster = cluster;
            json["tablesSchema"] = JObject.FromObject(tablesSchema);
            json["tablesCache"] = JObject.FromObject(tablesCache);
            json["tablesEagerCache"] = JObject.FromObject(tablesEagerCache);
            json["tablesAutoRefresh"] = JObject.FromObject(tablesAutoRefresh);
            json["tablesAlias"] = JObject.FromObject(tablesAlias);
            json["tablesDescription"] = JObject.FromObject(tablesDescription);
            json["tablesTags"] = JObject.FromObject(tablesTags);
            json["storageLevel"] = JObject.FromObject(storageLevel);
            json.userSubscription = userSubscription;
            json.serviceSubscription = serviceSubscription;
            json.enableQueryCache = enableQueryCache;
            json.connectorCacheEnabled = connectorCacheEnabled;
            json.shouldUpdateTree = shouldUpdateTree;
            json["specificColumns"] = JObject.FromObject(specificColumns);
            json["fetchSize"] = JObject.FromObject(fetchSize);
            json["partitionSize"] = JObject.FromObject(partitionSize);
            json["queryTimeout"] = JObject.FromObject(queryTimeout);
            json["adSubscriptions"] = JObject.FromObject(adSubscriptions);
            json["authenticationRadios"] = JArray.FromObject(authenticationRadios);
            json.displayName = displayName;
            json["databaseValue"] = JObject.FromObject(databaseValue);
            json.selection = selection;
            json["dataSourceConnection"] = JObject.FromObject(dataSourceConnection);
            json.isAuthorizationEnabled = isAuthorizationEnabled;
            payload = new StringContent(json.ToString(), Encoding.UTF8, "application/json");
            return payload;
        }
        public StringContent getPayload() { return payload; }

        public void setColumns(response_interface response, List<String> tablesname, string requestParameter)
        {
            Dictionary<string, Object> partitionColumnsList = new Dictionary<string, Object>();

            JArray array = ((db_response)response).jsonObject;
            foreach (string table in tablesname)
            {
                foreach (Object element in array)
                {
                    JObject objTables = JObject.Parse(JsonConvert.SerializeObject(element));
                    JArray tables = JArray.Parse(JsonConvert.SerializeObject(objTables.GetValue("tables")));
                    foreach (Object tableValue in tables)
                    {
                        JObject objTable = JObject.Parse(JsonConvert.SerializeObject(tableValue));
                        if (objTable.GetValue("name").ToString().Equals(table))
                        {
                            if (requestParameter.ToUpper().Equals("COLUMNS"))
                            {
                                JArray cols = JArray.Parse(JsonConvert.SerializeObject(objTable.GetValue("columns")));
                                this.tablesSchema.Add(table, cols);
                            }
                            else if (requestParameter.ToUpper().Equals("PARTITION_COLUMNS"))
                            {
                                JArray cols = JArray.Parse(JsonConvert.SerializeObject(objTable.GetValue("partitionColumns")));
                                partitionColumnsList.Add(table, cols);
                            }
                        }
                    }
                }
            }
            if (requestParameter.ToUpper().Equals("PARTITION_COLUMNS"))
                this.specificColumns.Add("partitionColumnsList", partitionColumnsList);
        }

        public void setStorageLevel(List<String> tablesname)
        {
            Dictionary<string, string> storageLevel = new Dictionary<string, string>();
            foreach (string tablename in tablesname)
            {
                storageLevel.Add(tablename, "DISK_ONLY");
            }
            this.specificColumns.Add("storageLevel", storageLevel);
            this.storageLevel = storageLevel;
        }

        public void setPartitionColumn(List<String> tablesname)
        {
            Dictionary<string, string> partitionColumn = new Dictionary<string, string>();
            foreach (string tablename in tablesname)
            {
                partitionColumn.Add(tablename, "");
            }
            this.specificColumns.Add("partitionColumn", partitionColumn);
        }

        public void setPartitionCount(List<String> tablesname, int partitionCountNr)
        {
            Dictionary<string, int> partitionCount = new Dictionary<string, int>();
            foreach (string tablename in tablesname)
            {
                partitionCount.Add(tablename, partitionCountNr);
            }
            this.specificColumns.Add("partitionCount", partitionCount);
        }

        public void setSpecificColumns(List<String> tablesname, response_interface response,int partitionCount)
        {
            setStorageLevel(tablesname);
            setPartitionColumn(tablesname);
            setPartitionCount(tablesname,partitionCount);
            setColumns(response, tablesname, "PARTITION_COLUMNS");
        }

        public void setAuthenticationRadios()
        {

            foreach (KeyValuePair<string, string> pairValue in const_value.getAuthenticationType())
            {
                Dictionary<string, string> authentication = new Dictionary<string, string>();
                authentication.Add("realValue", pairValue.Key);
                authentication.Add("displayValue", pairValue.Value);
            }
            this.authenticationRadios = authenticationRadios;
        }

        public void setDatabaseValue()
        {
            Dictionary<string, Object> databaseValue = new Dictionary<string, Object>();
            databaseValue.Add("value", true);
            databaseValue.Add("validators", new List<string>() { });

            this.databaseValue = databaseValue;
        }

        public void setDataSourceConnection(DBRequest dbRequest)
        {
            Dictionary<string, string> dataSourceConnection = new Dictionary<string, string>();
            dataSourceConnection.Add("database", dbRequest.name);
            dataSourceConnection.Add("connectionUrl", dbRequest.connectionUrl);
            dataSourceConnection.Add("connectionUsername", dbRequest.connectionUsername);
            byte[] plainTextBytes = System.Text.Encoding.UTF8.GetBytes(connectionPassword);
            string chryptedConnectionPassword = System.Convert.ToBase64String(plainTextBytes);
            dataSourceConnection.Add("connectionPassword", chryptedConnectionPassword);

            this.dataSourceConnection = dataSourceConnection;
        }

    }
}
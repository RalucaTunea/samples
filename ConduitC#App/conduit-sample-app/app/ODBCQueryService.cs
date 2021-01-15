using System.Data.Odbc;

using NLog;

namespace service
{
    public class ODBCQueryService
    {
        private static Logger logger = LogManager.GetCurrentClassLogger();
        public void executeODBCQueries(string typeName, string connectorName, string tableName)
        {
            OdbcConnectionStringBuilder builder = new OdbcConnectionStringBuilder
            {
                Driver = "Simba Spark ODBC Driver"
            };

            builder.Add("Host", "localhost");
            builder.Add("Port", "10002");
            builder.Add("Schema", "");
            builder.Add("HiveServerType", 2);
            builder.Add("UID", "");
            builder.Add("PWD", "");
            builder.Add("HTTPPath", "cliservice");
            builder.Add("ThriftTransport", 2);
            builder.Add("AuthMech", 3);

            using (OdbcConnection connection = new OdbcConnection(builder.ConnectionString))
            {
                string sqlQuery = "Select * from " + "`" + typeName + "_" + connectorName + "`" + ".`" + tableName + "`" + "limit 10";
                OdbcCommand command = new OdbcCommand(sqlQuery, connection);
                connection.Open();
                OdbcDataReader reader = command.ExecuteReader();

                logger.Info("\n");

                var tableQuery = reader.GetSchemaTable();
                foreach (System.Data.DataRow row in tableQuery.Rows)
                {
                    foreach (System.Data.DataColumn col in tableQuery.Columns)
                    {
                        logger.Info("{0} = {1}", col.ColumnName, row[col]);
                    }
                    logger.Info("============================");
                }

                reader.Close();
                command.Dispose();
            }
        }
    }
}

using System;
using System.Collections.Generic;
using System.Threading.Tasks;

using NLog;

using api_client = api.ApiClient;
using create_request = request.CreateConnectorRequest;
using create_response = response.CreateConnectorResponse;
using db_request = request.DBRequest;
using db_response = response.DBResponse;
using request_interface = request.IRequest;
using response_interface = response.IResponse;
using root = roots.Roots;

namespace service
{
    public class CreateConnectorService
    {

        private static Logger logger = LogManager.GetCurrentClassLogger();
        create_request connector = new create_request();

        public CreateConnectorService(create_request connectorValue)
        {
            connector = connectorValue;
        }
        public response_interface createRequest(request_interface createConnectorRequest, response_interface dbResponse)
        {

            createConnectorRequest.createPayload();
            create_response createConnectorResponse = new create_response();
            var task = Task.Run(() => api_client.post(createConnectorRequest, root.createRequestURL, createConnectorResponse));
            task.Wait();
            logger.Info(task.Result.ToString());
            return task.Result;
        }
        public void setCreateConnectorRequest(response_interface response, db_request dbRequest, String typeName, List<String> tablesname, String authenticationType, int partitionCount, Boolean isAuthorizationEnabled, String userSubscription)
        {

            connector.name = dbRequest.name;
            connector.database = ((db_response)response).jsonObject[0]["name"].ToString();
            connector.connectionUrl = dbRequest.connectionUrl;
            connector.connectionUsername = dbRequest.connectionUsername;
            connector.connectionPassword = dbRequest.connectionPassword;
            connector.typeName = typeName;
            connector.tableNames = tablesname;
            connector.authenticationType = authenticationType;

            connector.setColumns(response, tablesname, "COLUMNS");

            connector.setSpecificColumns(tablesname, response, partitionCount);

            connector.setAuthenticationRadios();

            connector.setDatabaseValue();

            connector.setDataSourceConnection(dbRequest);
        }
    }
}
using System;
using System.Net.Http;
using System.Threading.Tasks;
using System.Collections.Generic;

using NLog;

using api_client = api.ApiClient;
using existing_connector_response = response.ExistingConnectorResponse;
using db_request = request.DBRequest;
using db_response = response.DBResponse;
using request_interface = request.IRequest;
using response_interface = response.IResponse;
using root = roots.Roots;

namespace service
{
    public class DBService
    {
        private db_request dbRequest;
        private static Logger logger = LogManager.GetCurrentClassLogger();
        public response_interface dbrequest(request_interface dbRequest)
        {
            StringContent payload2 = dbRequest.createPayload();
            db_response dbResponse = new db_response();
            var task = Task.Run(() => api_client.post(dbRequest, root.dbRequestURL, dbResponse));
            logger.Info(task.Result.ToString());
            task.Wait();
            return task.Result;
        }

        public void setDBRequest(string connectionUsernameValue, string connectionPasswordValue, string nameValue, string urlValue, string authenticationTypeValue)
        {
            existing_connector_response existingConnectorResponse = new existing_connector_response();
            var task = Task.Run(() => api_client.get(null, root.datasourceNameURL, existingConnectorResponse));
            List<string> connectorList = ((existing_connector_response)task.Result).jsonObject;
            logger.Info("Request to verify that the datasource is not already in list has the response {} ", connectorList);
            task.Wait();
            if (connectorList.Contains(nameValue))
            {
                logger.Error("The connector already exist");
                Environment.Exit(1);
            }
            dbRequest = new db_request(connectionUsernameValue, connectionPasswordValue, nameValue, urlValue, authenticationTypeValue);
        }

        public db_request getDBRequest() { return dbRequest; }
    }
}



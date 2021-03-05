using System;
using System.Threading.Tasks;

using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

using NLog;

using api_client = api.ApiClient;
using create_response = response.CreateConnectorResponse;
using execute_query_request = request.ExecuteQueryRequest;
using execute_query_reponse = response.ExecuteQueryResponse;
using query_metadata_response = response.QueryMetadataResponse;
using request_interface = request.IRequest;
using response_interface = response.IResponse;
using route = routes.Routes;

namespace service
{
    public class RESTQueryService
    {
        private static Logger logger = LogManager.GetCurrentClassLogger();
        public response_interface getQueryMetadata()
        {
            query_metadata_response queryMetadataResponse = new query_metadata_response();
            var task = Task.Run(() => api_client.get(null, route.metadataAll, queryMetadataResponse));
            return task.Result;
        }
        public Boolean isValidConnectorName(query_metadata_response response, string connectorName, string connectorType)
        {
            Boolean found = false;
            foreach (Object connector in response.jsonObject)
            {
                JObject connectorObject = JObject.Parse(JsonConvert.SerializeObject(connector));
                string name = connectorObject.GetValue("name").ToString();
                if (name.Equals((connectorType + "_" + connectorName)))
                    found = true;
            }

            if (!found)
            {
                logger.Info("Connector does not exist in list of connectors for query");
                return false;
            }
            return true;
        }
        public response_interface executeQuery(request_interface executeQueryRequest, response_interface response)
        {
            executeQueryRequest.createPayload();
            create_response createConnectorResponse = new create_response();
            var task = Task.Run(() => api_client.post(executeQueryRequest, route.queryURL, response));
            logger.Info("Response from query request\n"+task.Result.ToString());
            return task.Result;
        }

        public response_interface getResult(request_interface executeQueryRequest, response_interface response, string queryId)
        {
            var task = Task.Run(() => api_client.get(executeQueryRequest, route.queryURL + queryId + "result", response));
            logger.Info("Response from result request\n"+task.Result.ToString());
            return task.Result;
        }
        public void executeRestQueries(string connectorName, string tableName,string connectorType)
        {
            RESTQueryService restQueryService = new RESTQueryService();
            response_interface metadata = restQueryService.getQueryMetadata();
            logger.Info("Display metadata " + metadata.status);
            execute_query_reponse executeQueryResponse = new execute_query_reponse();
            execute_query_request executeQueryRequest = new execute_query_request(connectorType, connectorName, tableName);
            logger.Info(restQueryService.isValidConnectorName((query_metadata_response)metadata, connectorName, connectorType));
            execute_query_reponse resultQuery = (execute_query_reponse)restQueryService.executeQuery(executeQueryRequest, executeQueryResponse);
            
            string queryId = resultQuery.jsonObject.GetValue("queryId").ToString();
            string status = resultQuery.jsonObject.GetValue("status").ToString();

            while (string.Equals(status,"Running"))
            {
                execute_query_reponse result = (execute_query_reponse)restQueryService.getResult(null, executeQueryResponse, queryId);
                status = result.jsonObject.GetValue("status").ToString();
            }
        }
    }
}

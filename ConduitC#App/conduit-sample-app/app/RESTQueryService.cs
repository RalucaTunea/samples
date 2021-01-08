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
using root = roots.Roots;

namespace service
{
    public class RESTQueryService
    {
        private static Logger logger = LogManager.GetCurrentClassLogger();
        public response_interface getQueryMetadata()
        {
            query_metadata_response queryMetadataResponse = new query_metadata_response();
            var task = Task.Run(() => api_client.get(null, root.metadataAll, queryMetadataResponse));
            task.Wait();
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
            var task = Task.Run(() => api_client.post(executeQueryRequest, root.queryURL, response));
            task.Wait();
            logger.Info(task.Result.ToString());
            return task.Result;
        }
        public void executeRestQueries(string connectorName, string tableName)
        {
            RESTQueryService restQueryService = new RESTQueryService();
            response_interface metadata = restQueryService.getQueryMetadata();
            logger.Info("Display metadata " + metadata.status);
            execute_query_reponse executeQueryResponse = new execute_query_reponse();
            execute_query_request executeQueryRequest = new execute_query_request("oracle", connectorName, tableName);
            logger.Info(restQueryService.isValidConnectorName((query_metadata_response)metadata, connectorName, "oracle"));
            restQueryService.executeQuery(executeQueryRequest, executeQueryResponse);
        }
    }
}

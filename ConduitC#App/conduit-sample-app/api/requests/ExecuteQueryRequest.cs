using System.Text;
using System.Net.Http;

using Newtonsoft.Json.Linq;


namespace request
{
    public class ExecuteQueryRequest : IRequest
    {
        public string queryId { get; set; } = null; 
        public string query { get; set; }
        public int  offset { get; set; } = 0;
	    public int limit { get; set; } = 10;
        public string connectorType { get; set; }
        public string connectorName { get; set; }
        public string tableName { get; set; }
        private StringContent payload;

        public ExecuteQueryRequest(string connectorTypeValue, string connectorNameValue, string tableNameValue)
        {
            connectorType = connectorTypeValue;
            connectorName = connectorNameValue;
            tableName = tableNameValue;
        }
        public StringContent createPayload()
        {
            JObject obj = new JObject();
            obj.Add("queryId", queryId);
            query = "SELECT * FROM " + connectorType+"_"+ connectorName+ "." + tableName;
            obj.Add("query", query);
            obj.Add("offset", offset);
            obj.Add("limit",limit);
            payload = new StringContent(obj.ToString(), Encoding.UTF8, "application/json");
            return payload;
        }
        public StringContent getPayload() { return payload; }

    }
}
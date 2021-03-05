using System;
using System.Text;
using System.Net.Http;

using System.Collections.Generic;

using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace request
{
    public class DBRequest : IRequest
    {
        public string name { get; set; }
        public string description { get; set; } = "";
        public string connectionUrl { get; set; }
        public string connectionUsername { get; set; }
        public string connectionPassword { get; set; }
        public string authenticationType { get; set; }
        public string subscriptionId { get; set; } = "";
        public StringContent payload { get; set; }
    
        public DBRequest(string connectionUsernameValue, string connectionPasswordValue, string nameValue, string urlValue, string authenticationTypeValue)
        {
            name = nameValue;
            connectionUsername = connectionUsernameValue;
            connectionPassword = connectionPasswordValue;
            connectionUrl = urlValue;
            authenticationType = authenticationTypeValue;
            name = nameValue;
        }
        public StringContent createPayload()
        {
            JObject obj = JObject.Parse(JsonConvert.SerializeObject(this));
            obj.Add("namespace", "default");
            byte[] plainTextBytes = System.Text.Encoding.UTF8.GetBytes(connectionPassword);
            string chryptedConnectionPassword = System.Convert.ToBase64String(plainTextBytes);
            obj.Remove("connectionPassword");
            obj.Add("connectionPassword", chryptedConnectionPassword);
            obj.Add("parquetPartitionColumns",JObject.FromObject(new Dictionary<string, Object>()));
            payload = new StringContent(obj.ToString(), Encoding.UTF8, "application/json");

            return payload;
        }
        public StringContent getPayload() { return payload; }

        public override string ToString()
        {
            return "DBRequest {" +
           "name='" + name + '\'' +
           ", connectionUsername='" + connectionUsername + '\'' +
           ", connectionPassword='" + connectionPassword + '\'' +
           ", connectionUrl='" + connectionUrl + '\'' +
           ", authenticationType='" + authenticationType + "}" + '\'' ;
        }
    }
}
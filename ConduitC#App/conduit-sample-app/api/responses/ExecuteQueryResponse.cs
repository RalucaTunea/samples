using System.Net.Http;
using System.IO;
using System.Text;

using Newtonsoft.Json.Linq;

namespace response
{
    public class ExecuteQueryResponse : IResponse
    {
        public JObject jsonObject { get; set; } = new JObject();
        public string status { get; set; }
        public IResponse parseResponse(HttpResponseMessage responseRequest)
        {

            if (responseRequest.IsSuccessStatusCode)
            {
                Stream receiveStream = responseRequest.Content.ReadAsStream();
                StreamReader readStream = new StreamReader(receiveStream, Encoding.UTF8);
                string content = readStream.ReadToEnd();
                jsonObject = JObject.Parse(content);
                status = responseRequest.StatusCode.ToString();
            }
            return this;
        }

        public override string ToString()
        {
            return "ExecuteQueryRequest response {" + jsonObject + "}";
        }
        
        public JObject getJResult(){
            return jsonObject;
        }

    }
}
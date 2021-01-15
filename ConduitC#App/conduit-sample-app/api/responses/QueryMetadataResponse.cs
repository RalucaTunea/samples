using System.Net.Http;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.IO;
using System.Text;

using Newtonsoft.Json.Linq;

namespace response
{
    public class QueryMetadataResponse : IResponse
    {
        public JArray jsonObject { get; set; }

        public string status { get; set; }
        public IResponse parseResponse(HttpResponseMessage responseRequest)
        {

            if (responseRequest.IsSuccessStatusCode)
            {
                Stream receiveStream = responseRequest.Content.ReadAsStream();
                StreamReader readStream = new StreamReader (receiveStream, Encoding.UTF8);
                string content = readStream.ReadToEnd();
                jsonObject = jsonObject = JArray.Parse(content).ToObject<JArray>();
                status = responseRequest.StatusCode.ToString();
            }
            return this;
        }

        public override string ToString()
        {
            return "Query metadata response : " + jsonObject.ToString();
        }
    }
}
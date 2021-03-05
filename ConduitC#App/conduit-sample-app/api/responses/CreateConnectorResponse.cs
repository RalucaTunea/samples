using System.Net.Http;
using System.Threading.Tasks;
using System.IO;
using System.Text;
using System;

using Newtonsoft.Json.Linq;

namespace response
{
    public class CreateConnectorResponse : IResponse
    {
        public JObject jsonObject { get; set; } = new JObject();
        public string status { get; set; }
        public IResponse parseResponse(HttpResponseMessage responseRequest)
        {

            if (responseRequest.IsSuccessStatusCode)
            {
                Stream receiveStream = responseRequest.Content.ReadAsStream();
                StreamReader readStream = new StreamReader (receiveStream, Encoding.UTF8);
                string content = readStream.ReadToEnd();
                if (content != "")
                    jsonObject = JObject.Parse(content);
                    status = responseRequest.StatusCode.ToString();
            }

            return this;
        }

        public override string ToString()
        {
            return "CreateRequest response {"+ jsonObject +"}";
        }
    }
}
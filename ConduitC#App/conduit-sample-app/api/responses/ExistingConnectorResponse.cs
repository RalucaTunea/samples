using System.Net.Http;
using System.Collections.Generic;
using System.IO;
using System.Text;

using Newtonsoft.Json.Linq;

using NLog;

namespace response
{
    public class ExistingConnectorResponse : IResponse
    {
        private static Logger logger = LogManager.GetCurrentClassLogger();
        public List<string> jsonObject { get; set; }

        public string status { get; set; }
        public IResponse parseResponse(HttpResponseMessage responseRequest)
        {

            if (responseRequest.IsSuccessStatusCode)
            {
                Stream receiveStream = responseRequest.Content.ReadAsStream();
                StreamReader readStream = new StreamReader(receiveStream, Encoding.UTF8);
                string content = readStream.ReadToEnd();
                logger.Info("Display the response for existing request " + content);
                jsonObject = JArray.Parse(content).ToObject<List<string>>();
                status = responseRequest.StatusCode.ToString();
            }
            return this;
        }

        public override string ToString()
        {
            return "Existing connectors list: " + jsonObject.ToString();
        }
    }
}
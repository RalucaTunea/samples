using System.Net.Http;
using System.IO;
using System.Text;

using Newtonsoft.Json.Linq;

using NLog;

namespace response
{
    public class LoginResponse : IResponse
    {
        public string token { get; set; }
        public string status { get; set; }
        private static Logger logger = LogManager.GetCurrentClassLogger();
        public IResponse parseResponse(HttpResponseMessage responseRequest)
        {

            if (responseRequest.IsSuccessStatusCode)
            {
                Stream receiveStream = responseRequest.Content.ReadAsStream();
                StreamReader readStream = new StreamReader(receiveStream, Encoding.UTF8);
                string content = readStream.ReadToEnd();
                JObject jsonToken = JObject.Parse(content);

                token = jsonToken.GetValue("jwtToken").ToString();
                status = responseRequest.StatusCode.ToString();
            }
            return this;
        }

        public override string ToString()
        {
            return
                "Login Request result '{" +
                   "token='" + token + "}";
        }

        public void setToken(string authToken){
             token = authToken;
        }
    }
}
using System.Net.Http;
using System.Threading.Tasks;
using System.Net.Http.Headers;

using NLog;

using response_interface = response.IResponse;
using request_interface = request.IRequest;

namespace api
{
    public class ApiClient
    {
        private static Logger logger = LogManager.GetCurrentClassLogger();
        public static string token { get; set; } = null;
        private static ApiClient client;
        private ApiClient() { }
        public static ApiClient getInstance()
        {
            if (client == null)
            {
                client = new ApiClient();
            }
            return client;
        }
        public static response_interface post(request_interface request, string url, response_interface response)
        {
            var handler = new HttpClientHandler();

            handler.ServerCertificateCustomValidationCallback +=
                 (sender, certificate, chain, errors) =>
                 {
                     return true;
                 };

            HttpClient client = new HttpClient(handler);
            if (token != null)
            {
                var authenticationHeaderValue = new AuthenticationHeaderValue("Bearer", token);
                client.DefaultRequestHeaders.Authorization = authenticationHeaderValue;
            }

            StringContent payload = request.getPayload();

            HttpRequestMessage request1 = new HttpRequestMessage(HttpMethod.Post, url);

            request1.Content = payload;

            var task = Task.Run(() => client.Send(request1));

            return response.parseResponse(task.Result);
        }
        public static async Task<response_interface> get(request_interface request, string url, response_interface response)
        {
            var handler = new HttpClientHandler();

            handler.ServerCertificateCustomValidationCallback +=
                 (sender, certificate, chain, errors) =>
                 {
                     return true;
                 };

            HttpClient client = new HttpClient(handler);
            if (token != null)
            {
                var authenticationHeaderValue = new AuthenticationHeaderValue("Bearer", token);
                client.DefaultRequestHeaders.Authorization = authenticationHeaderValue;
            }

            HttpResponseMessage responseReq = await client.GetAsync(url);

            return response.parseResponse(responseReq);

        }
    }
}
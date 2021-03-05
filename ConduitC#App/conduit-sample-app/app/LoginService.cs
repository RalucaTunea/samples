using System.Net.Http;
using NLog;

using api_client = api.ApiClient;
using login_request = request.LoginRequest;
using login_response = response.LoginResponse;
using response_interface = response.IResponse;
using route = routes.Routes;
using user = entity.User;

namespace conduit_sample_app
{
    public class LoginService
    {
        private static Logger logger = LogManager.GetCurrentClassLogger();
        public response_interface login(string username, string password)
        {
            response_interface response;
            login_response loginResponse = new login_response();
            user user = new user(username, password);
            login_request loginRequest = new login_request(user);
            StringContent payload = loginRequest.createPayload();
            response = api_client.post(loginRequest, route.loginURL, loginResponse);
            
            api_client.token = loginResponse.token;
            
            return response;
        }

        public response_interface login(string token)
        {
            response_interface response;
          
            login_response loginResponse = new login_response();
            loginResponse.setToken(token);
            response = loginResponse;
            api_client.token = loginResponse.token;
            loginResponse.status = "OK";
            logger.Info("Status"+ response.status);
            
            return response;
        }
    }
}
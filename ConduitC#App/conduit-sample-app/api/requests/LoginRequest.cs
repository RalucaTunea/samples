using System.Text;
using System.Net.Http;

using Newtonsoft.Json;

using user = entity.User;

namespace request
{
    public class LoginRequest : IRequest
    {
        private user user;
        private StringContent payload;
        public LoginRequest(user userValue)
        {
            user = userValue;
        }
        public StringContent createPayload()
        {
            payload = new StringContent(JsonConvert.SerializeObject(user), Encoding.UTF8, "application/json");
            return payload;
        }
        public StringContent getPayload() { return payload; }

        public override string ToString()
        {
            return "LoginRequest {" +
             "user='" + user.ToString() + "}";
        }

    }
}
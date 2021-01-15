using System.Net.Http;

namespace request
{
    public interface IRequest
    {
        public StringContent getPayload();
        public StringContent createPayload();
    }
}
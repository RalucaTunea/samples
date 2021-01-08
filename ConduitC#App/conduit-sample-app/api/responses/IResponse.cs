using System.Net.Http;

namespace response
{
    public interface IResponse
    {
        public IResponse parseResponse(HttpResponseMessage response);
        public string status { get; set; }
    }
}
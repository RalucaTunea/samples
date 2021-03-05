namespace routes
{
    public class Routes
    {
        public static string urlRoot = config.AppConfig.getEnvironment();
        public static string createRequestURL = urlRoot + "/api/metadata/datasources";
        public static string dbRequestURL = urlRoot + "/api/metadata/explore/oracle";
        public static string datasourceNameURL = urlRoot + "/api/metadata/datasourcesNames";
        public static string loginURL = urlRoot + "/auth";
        public static string metadataAll = urlRoot + "/query/metadata/all";
        public static string queryURL = urlRoot + "/query/execute";
    }
}
using System;
using System.Collections.Generic;
using System.Data.Odbc;

using NLog;

using Xunit;

using api_client = api.ApiClient;
using app_config = config.AppConfig;
using create_request = request.CreateConnectorRequest;
using create_service = service.CreateConnectorService;
using db_response = response.DBResponse;
using db_service = service.DBService;
using odbc_query_service = service.ODBCQueryService;
using rest_query_service = service.RESTQueryService;
using response_interface = response.IResponse;

namespace conduit_sample_app
{
    public class App
    {
        private static Logger logger = LogManager.GetCurrentClassLogger();
        static void Main(string[] args)
        {   
            app_config appConfig = new app_config(); 
        
    
            api_client apiClient = api_client.getInstance();
            LoginService loginService = new LoginService();
            response_interface loginResponse;

            if (appConfig.getToken() != "")
            { 
                //authentication request AD mode
                loginResponse = loginService.login(appConfig.getToken());
                logger.Info("Display status for login: {}", loginResponse.status);
                Assert.Equal("OK", loginResponse.status);
            }
            else{
            //authentication request 
            loginResponse = loginService.login(appConfig.getEmail(), appConfig.getPassword());
            logger.Info("Display status for login: {}", loginResponse.status);
            Assert.Equal("OK", loginResponse.status);
            }
            //database request
            db_service dbService = new db_service();
            dbService.setDBRequest(appConfig.getConnectionUsername(), appConfig.getPassword(), appConfig.getConnectorName(), appConfig.getDataSourceConnecion(), appConfig.getAuthenicationType());
            db_response dbResponse = (db_response)dbService.dbrequest(dbService.getDBRequest());
            logger.Info("Display status for dbRequest: {}", dbResponse.status);
            Assert.Equal("OK", dbResponse.status);

            //create request
            create_request createConnectorRequest = new create_request();
            create_service createConnectorService = new create_service(createConnectorRequest);
            createConnectorService.setCreateConnectorRequest(dbResponse, dbService.getDBRequest(), appConfig.getTypeName(), appConfig.getTables(), appConfig.getAuthenicationType(), appConfig.getPartitionCount(), appConfig.getIsAuthorizationEnabled());
            response_interface createResponse = createConnectorService.createRequest(createConnectorRequest, dbResponse);
            logger.Info("Display status for create request: {}", createResponse.status);
            Assert.Equal("OK", createResponse.status);

            //SQL QUERY with ODBC
            // odbc_query_service requestODBCService = new odbc_query_service();
            // requestODBCService.executeODBCQueries(appConfig.getTypeName(), appConfig.getConnectorName(), appConfig.getTableName());

            //REST QUERY
            rest_query_service requestQueryService = new rest_query_service();
            requestQueryService.executeRestQueries(appConfig.getConnectorName(), appConfig.getTableName(), appConfig.getTypeName());
        }
    }
}
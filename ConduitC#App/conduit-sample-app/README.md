#### Project Setup
    1. We need as conduit server to be in run mode. Infos about how to setup server of conduit or how to run it, please see bde/README.md
    2. Need to install SimbaApacheSparkODBCDriver to have connection to the connector for SQL queries from https://www.simba.com/drivers/spark-jdbc-odbc/ .Also it is necessary to put the license in the required place (for exemple on Mac the required place is: /Users/username)(the licence is received in email, after an acount is created on specified link)

To verify the exitsence of the driver use the following commands in terminal: cat /usr/local/etc/odbcinst.ini 
The result of the command has to look like:

[ODBC Driver 17 for SQL Server]
Description = Microsoft ODBC Driver 17 for SQL Server
Driver      = /usr/local/lib/libmsodbcsql.17.dylib
UsageCount  = 1

[Simba Spark ODBC Driver]
Driver = /Library/simba/spark/lib/libsparkodbc_sbu.dylib

#### Packages
    - conduit-sample-app/api pacakge conatains all code about REST API
          -requests : these get all informations from user and use it for request, so we there are  requests  for login, database  and create connector
          -response : these get the respose from API methods and parse it, so there are responses for login, database and create connector
          -ApiClient : this conatins the API methods: post
          -utils package is used to store consts parameters

    - conduit-sample-app/app pacakge conatins services classes
          -services classes call the request methods from api using request entity and display the results
          -App - is the main class for app
          -entities are the auxiliary classes used to store all data necessary for requests and responses 

    - conduit-sample-app/config package contains class AppConfig 
          -AppConfig - manages the user inputs from terminal

#### Run
    - the App class represents the main and in it at the begininig are set the data for create request and sql querries, here in App class the infos can be changed
    - the sample app uses just basic informations for connector creation
    - loggers have to display - with Info mode: the status of the request and also the request results
                                e.g |INFO|conduit_sample_app.App|Display status login: "OK"
                              - with Error mode: the problem, for exemple BAD REQUEST
                                e.g |ERROR|conduit_sample_app.DBService|The connector already exist
    - the sample app contains two ways to perform query, one using ODBC and another using REST

    Steps to run the sample:
        1.open a terminal
        2.go to the folder that contains the app and use command cd to be inside of sample
          e.g user@192-168-0-104 conduit-sample-app % 
        3.run this command on terminal
            EMAIL="email" PASSWORD="password" AUTHENTICATION_TYPE="anonymous_with_impersonation" CONNECTION_URL="url" CONNECTION_USERNAME="username" SUBSCRIPTION_ID="" TYPE_NAME="oracle"  TABLES="table1,table2" PARTITION_COUNT="4" IS_AUTHORIZATION_ENABLED="false" TABLE_NAME="table1" CONNECTOR_TYPE="oracle"  CONNECTOR_NAME="connector_name" dotnet run -p conduit-sample-app.csproj
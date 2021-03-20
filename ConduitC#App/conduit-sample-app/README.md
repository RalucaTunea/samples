### C# Conduit Sample App
#### Project Setup
1. To run the sample app one needs the Conduit server up and running.
2. To run the sample another need .NET SDK  installed, see https://dotnet.microsoft.com/download
   To see if dotnet was intalled run: dotnet --info
   As result :
   Host (useful for support):
   Version: 5.0.3
   Commit:  c63bb5dc84
   .NET SDKs installed:
   5.0.103 [/usr/local/share/dotnet/sdk]
   .NET runtimes installed:
    
   Microsoft.AspNetCore.App 5.0.3 [/usr/local/share/dotnet/shared/Microsoft.AspNetCore.App]
   Microsoft.NETCore.App 5.0.3 [/usr/local/share/dotnet/shared/Microsoft.NETCore.App]
 
   To install additional .NET runtimes or SDKs:
   https://aka.ms/dotnet-download

3. You also need to install the Simba driver for running queries over ODBC on the Apache Spark server, see https://www.simba.com/drivers/spark-jdbc-odbc/
If you've installed the driver correctly you should be able to:
`cat /usr/local/etc/odbcinst.ini`
The file should look something like this:
```
[ODBC Driver 17 for SQL Server]
Description = Microsoft ODBC Driver 17 for SQL Server
Driver      = /usr/local/lib/libmsodbcsql.17.dylib
UsageCount  = 1
[Simba Spark ODBC Driver]
Driver = /Library/simba/spark/lib/libsparkodbc_sbu.dylib
```
#### Project structure
- conduit-sample-app/api - contains code for accessing the API via REST
      - requests: contains all requests needed for querying the API
      - response: contains all responses that the API sends back to our app
      - utils: contains utility code such as constants and routes
      - ApiClient: contains helper methods for GETing and POSTing to the API
- conduit-sample-app/app - contains the main section of the APP
      - entities are used to store all data necessary for requests and from responses
      - all services are mapped to their own service classes breaking down interaction based on actions
      - App - is the main class for app
- conduit-sample-app/config/AppConfig - used to parse user inputs (environment variables)

#### Logging
Logging involves either INFO messages or WARNING messages

#### Running
In order to run either app we need to configure ENV Variables. The environment variables used to run are:
- EMAIL - used for logging in with a Conduit user
  <br/>e.g EMAIL="mail@bpcs.com"
- PASSWORD - password for the Conduit user
  <br/>e.g PASSWORD="mypassword"
- TOKEN - AD user Access Token
  <br/>e.g TOKEN=yJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjcmVhdGVk98698Om51bGwsImNyZWF0ZWRCeSI6bnVsb344gc
- TABLES - database's tables
  <br/>e.g TABLES="TABLE1,TABLE2"
- AUTHENTICATION_TYPE - authentication type
  <br/>e.g AUTHENTICATION_TYPE=
- DATA_SOURCE_LOCATION - date set location when create a connector
  <br/>e.g DATA_SOURCE_LOCATION=localhost:1400/databaseName
- CONNECTION_USERNAME - database's username
  <br/>e.g CONNECTION_USERNAME="dataSourceUserName"
- CONNECTOR_NAME - used to specify a name to connector
  <br/>e.g CONNECTOR_NAME="connector_name"
- TYPE_NAME - used to specify the database type 
  <br/>e.g TYPE_NAME="oracle"
- PARTITION_COLUMNS_COUNT - number of partition columns
- IS_AUTHORIZATION - Enabled or disabled authorization. 
  <br/>e.g IS_AUTHORIZATION="false"
- URL - location of the Conduit server
  <br/>e.g URL=https://environment.com
- TABLE_NAME - name of table used for query 
  <br/>e.g TABLE_NAME="table1"

- there are two possible approaches used to interact with the data. Using the REST API, or using an ODBC connection (using Simba as the driver).
#### AD authentication
When running the sample app the user has the choice to authenticate with a standard Conduit user (by giving the EMAIL and PASSWORD env variables), or with an AD user.
The later scenario requires the access token which the user obtains by logging into the Conduit management console in a browser and copying the Access Token into the TOKEN environment variable.
**Note:** the access token takes precedence over user/pass combo. If give all 3 environment variables the app is going to be using the Access Token. 

- To run the app you run the following command in a terminal with the needed environment variables already set
    ``` bash
    dotnet run -p conduit-sample-app.csproj
    ```
- Full command template:
    ``` bash
    EMAIL="email" PASSWORD="password" AUTHENTICATION_TYPE="anonymous_with_impersonation" DATA_SOURCE_LOCATION="url" CONNECTION_USERNAME="username" TYPE_NAME="oracle"  TABLES="table1,table2" PARTITION_COUNT="4" IS_AUTHORIZATION_ENABLED="false" TABLE_NAME="table1"  CONNECTOR_NAME="connector_name" TOKEN="tokenValue" URL="environmentValue dotnet run -p conduit-sample-app.csproj
    ```
- Command to use application with active directory authentication:
    ``` bash
    EMAIL="email" PASSWORD="password" AUTHENTICATION_TYPE="anonymous_with_impersonation" DATA_SOURCE_LOCATION="url" CONNECTION_USERNAME="username" TYPE_NAME="oracle"  TABLES="table1,table2" PARTITION_COUNT="4" IS_AUTHORIZATION_ENABLED="false" TABLE_NAME="table1" CONNECTOR_NAME="connector_name" TOKEN="tokenValue" URL="environmentValue" dotnet run -p conduit-sample-app.csproj
    ```
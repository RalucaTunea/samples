#### Project Setup
1. To run the sample app one needs the Conduit server up and running.
2. You also need to install the Simba driver for running queries over ODBC on the Apache Spark server, see https://www.simba.com/drivers/spark-jdbc-odbc/

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

#### Packages
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

#### Run
- there are two possible approaches used to interact with the data. Using the REST API, or using an ODBC connection (using Simba as the driver).

- To run the app you run the following command in a terminal with the needed environment variables already set
    ``` bash
    dotnet run -p conduit-sample-app.csproj
    ```

- Full command template:
``` bash
    EMAIL="email" PASSWORD="password" AUTHENTICATION_TYPE="anonymous_with_impersonation" CONNECTION_URL="url" CONNECTION_USERNAME="username" SUBSCRIPTION_ID="" TYPE_NAME="oracle"  TABLES="table1,table2" PARTITION_COUNT="4" IS_AUTHORIZATION_ENABLED="false" TABLE_NAME="table1" CONNECTOR_TYPE="connectorType"  CONNECTOR_NAME="connector_name" TOKEN="tokenValue" ENVIRONMENT="environmentValue" dotnet run -p conduit-sample-app.csproj
    ```

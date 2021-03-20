### Java Conduit Sample App
#### Project structure
[>> api](./src/main/java/com/conduit/sample/api) - contains all requests and responses for the and from the API
    - response folder has responses from login, database and create connector
    - request folder has requests from login, database and create connector
    - ApiClient has - REST API methods (eg. post, get) 
                    - method for authentication - using SSLConnectionSocketFactory for handling SSL certificate
[>> services](./src/main/java/com/conduit/sample/services) - contains the Service classes used to interact with the Conduit API alongside entity's which are used to abstract Conduit internal structures
    - entity folder has -> Connector class, which is connector structure used in CreateConnectorRequest class to create an object to json 
                        -> ExploreRequest, which is database response's structure, it is used in DBResponse class to create response 
                        from json format in object format
    - service classes (CreateConnectorService and DBRequestService) used to interact with the API using the Requests and ApiClient
[>> config](./src/main/java/com/conduit/sample/config) - contains the AppConfig class  which manages the user input from terminal
#### Main elements of the App
There are two App classes in the project (JDBCApp and RESTApp). Both of them are executing the same Conduit interaction, the difference being the medium they use to execute it. Both apps:
                        - configure the sample based on user input
                        - execute the entire flow (login, db requests, connector creation, connector querying)
The JDBCApp class is the main JDBC class, it:
                        - achieves Conduit interaction by making use of the JDBC endpoint offered by Conduit
The RESTApp class is the main REST API class, it:
                        - achieves Conduit interaction by making use of Conduit's REST API
#### Running
In order to run either app we need to configure ENV Variables. The environment variables used to run are:
- EMAIL - used for logging in with a Conduit user
  <br/>e.g EMAIL=mail@bpcs.com
- PASSWORD - password for the Conduit user
  <br/>e.g PASSWORD=mypassword
- TOKEN - AD user Access Token
  <br/>e.g TOKEN=yJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjcmVhdGVk98698Om51bGwsImNyZWF0ZWRCeSI6bnVsb344gc
- TABLES - database's tables
  <br/>e.g TABLES=TABLE1,TABLE2
- AUTHENTICATION_TYPE - authentication type
  <br/>e.g AUTHENTICATION_TYPE=anonymous_with_impersonation
- DATA_SOURCE_LOCATION - date set location when create a connector
  <br/>e.g DATA_SOURCE_LOCATION=localhost:1400/databaseName
- USERNAME - database's username
  <br/>e.g USERNAME=dataSourceUserName
- DESCRIPTION - a short connector description
  <br/>e.g DESCRIPTION="This is a description field"
- CONNECTOR_NAME - used to specify a name to connector
  <br/>e.g CONNECTOR_NAME="connector_name"
- TYPE_DATABASE - used to specify the database type 
  <br/>e.g TYPE_DATABASE=oracle
- PARTITION_COLUMNS_COUNT - number of partition columns
- IS_AUTHORIZATION - Enabled or disabled authorization. 
  <br/>e.g IS_AUTHORIZATION=false
- JDBC_URL - used to query connector through JDBC endpoint
  <br/>e.g JDBC_URL=jdbc:hive2://<host>:<port>/<dbName>;<sessionConfs>?<hiveConfs>#<hiveVars>
- URL - location of the Conduit server
  <br/>e.g URL=https://environment.com
- DUSER_JDBC, DPASSWORD_JDBC  - credentials used to connect to JDBC
  <br/>e.g -DUSER_JDBC=JDBCName -DPASSWORD_JDBC=JDBCPassword
#### Logging
Logging involves either INFO messages or WARNING messages
**Example**
INFO: Create Connector Response: HTTP/1.1 200 OK 
or 
WARNING: Connector already exist.
WARNING: Invalid username or password. Please contact your Admin.
#### AD authentication
When running the sample app the user has the choice to authenticate with a standard Conduit user (by giving the EMAIL and PASSWORD env variables), or with an AD user.
The later scenario requires the access token which the user obtains by logging into the Conduit management console in a browser and copying the Access Token into the TOKEN environment variable.
**Note:** the access token takes precedence over user/pass combo. If give all 3 environment variables the app is going to be using the Access Token.

Full command template:
``` 
java -DEMAIL=email@bpcs.com -DPASSWORD=password -DTABLES=table1,table2 -DAUTHENTICATION_TYPE=anonymous_with_impersonation -DATA_SOURCE_LOCATION=localhost:1400/databaseName -DUSERNAME=dataSourceUserName -DDESCRIPTION=shortDescription -DCONNECTOR_NAME=connector_name -DTYPE_DATABASE=oracle -DPARTITION_COLUMNS_COUNT=4 -DIS_AUTHORIZATION=false -DTOKEN= -DUSER_JDBC=JDBCName -DPASSWORD_JDBC=JDBCPassword -DURL=https://environment.com -DJDBC_URL=jdbc:hive2://environment.com:10002/;transportMode=http;httpPath=cliservice;ssl=true com.conduit.sample.RESTApp
```

Command to use application with active directory authentication:
```
java -DEMAIL=email@bpcs.com -DPASSWORD=password -DTABLES=table1,table2 -DAUTHENTICATION_TYPE=anonymous_with_impersonation -DATA_SOURCE_LOCATION=localhost:1400/databaseName -DUSERNAME=dataSourceUserName -DDESCRIPTION=shortDescription -DCONNECTOR_NAME=connector_name -DTYPE_DATABASE=oracle -DPARTITION_COLUMNS_COUNT=4 -DIS_AUTHORIZATION=false -DTOKEN=yJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjcmVhdGVk98698Om51bGwsImNyZWF0ZWRCeSI6bnVsb344 -DUSER_JDBC=JDBCName -DPASSWORD_JDBC=JDBCPassword -DURL=https://environment.com -DJDBC_URL=jdbc:hive2://environment.com:10002/;transportMode=http;httpPath=cliservice;ssl=true com.conduit.sample.RESTApp
```
### Python Conduit Sample App
#### Project Structure
[>> api](src/api) - contains the classes with the information needed to create the payload for requests
[>> consts](src/consts) - contains the paths for the URL root and the payloads that will be modified to sent the requests
[>>interfaces](src/interfaces) - contains the main functionalities for creating a connector
            - here you can set the authentication and user information
            - sets the login with user and password
[>>payload](src/payload) - contains the payloads that will modified when sending the requests
[>>request](src/request) - sets the payload with the information from the user which is used to create the connector request
[>>service](src/service) - contains the logic that is needed to:
            - set the connector and create the datasource for it
            - basic logic for executing queries using the jaydebeapi library and via REST Api
[>>lib](src/lib) - file contains the jar that is needed to run the queries You can add the jar file from --> https://bpartifactstorage.blob.core.windows.net/hive-jdbc-driver/conduit_utils.jar

#### Main elements of the App
There are two App classes in the project (app-jdbc and app-rest). Both of them are executing the same Conduit interaction,
the difference being the way they use to execute it. Both apps:

- configure the sample based on user input
- execute the entire flow(login, db, requests, connector creation, connector querying)

The app-jdbc class is the main JDBC class, it achieves Conduit interaction by making use of the JDBC endpoint offered by Conduit.

The app-rest class is the main REST API class, it achieves Conduit interaction by making use of Conduit's REST API.

####Running

In order to run either app we need to configure ENV Variables. The environment variables used to run are:
- API_USER - used for logging in with a Conduit user
  e.g API_USER=mail@bpcs.com
- API_PASS - password for the Conduit user
  e.g API_PASS=mypassword
- OAUTH_TOKEN - AD user Access Token
  e.g OAUTH_TOKEN=yshbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjcmVhdGVk98698Om51bGwsImNyZWF0ZWRCeSI6bnVsb344gc
- TABLE_NAME - database's tables
  e.g TABLE_NAME=TABLE1,TABLE2
- AUTH_TYPE - authentication type
  e.g AUTH_TYPE=anonymous_with_impersonation
- DATA_SOURCE_LOCATION - date set location when create a connector
  e.g DATA_SOURCE_LOCATION=localhost:1400/databaseName
- USER - database's username
  e.g USER=dataSourceUserName
- PASS - database's password
  e.g PASS= password for database
- CONNECTOR_NAME - used to specify a name to connector
  e.g CONNECTOR_NAME="connector_name"
- URL - location of the Conduit server
  e.g URL=https://environment.com

And for JDBC test:
- HOST -location of the Conduit server provided for jdbc endpoint
  e.g URL=environment.com
- PORT - port number for jdbc endpoint
#### Logging
Logging involves either INFO messages or WARNING messages

#### AD authentication
When running the sample app the user has the choice to authenticate with a standard Conduit user (by giving the API_USER and API_PASS env variables), or with an AD user.
The later scenario requires the access token which the user obtains by logging into the Conduit management console in a browser and copying the Access Token into the OAUTH_TOKEN environment variable.
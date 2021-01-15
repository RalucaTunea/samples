#Project Setup
The conduit server is needed to be in run mode.
For more information about how to setup the server of conduit or how to run it, please see bde/README.md

#Packages

>> /lib file contains the jar that is needed to run the queries
You can add the jar file from --> https://bpartifactstorage.blob.core.windows.net/hive-jdbc-driver/conduit_utils.jar

>> /api contains the classes with the information needed to create the payload for requests

>> /interfaces contains the main functionalities for creating a connector
            - here you can set the authentication and user information
            - sets the login 

>> /payload contains the payloads added as default that will be modified to send for request

>> /request sets the payload with the information from the user which is used to create the connector request

>> /service(connector_service, query_sql_service and query_service) contains the logic that is needed to:
            - set the connector and create the datasource for it
            - basic logic for executing queries using the jaydebeapi library and via REST Api

>> /app is the main class where we execute queries using the jaydebeapi library and where we:
            -set the information for the user
            -call commands for setting and creating the connector
            -call the methods for executing queries

>> /app_rest_api is basically the same as app.py, but the queries are sent via REST Api
 
#Run
##IMPORTANT!
For running the program the user should set each variable in the command line and run the program with the python command
Example:
>API_USER="example@bpcs.com" API_PASS='password' CONN_USER="user" CONN_PASS='pass' AUTH_TYPE="authentication_without_impersonation" 
CONNECTOR_NAME="test1" URL="url" TABLE_NAME="PDBADMIN___BOOKS" python3 app.py

>>API_USER="example@bpcs.com" API_PASS='password' CONN_USER="user" CONN_PASS='pass' AUTH_TYPE="authentication_without_impersonation" 
CONNECTOR_NAME="test1" URL="url" TABLE_NAME="PDBADMIN___BOOKS" python3 app_rest_api.py

AUTH_TYPE="authentication_without_impersonation" --> for setting an authentication without impersonation connector
TABLE_NAME="PDBADMIN___BOOKS" --> this is the table that should be used for the example presented in app.py and app_rest_api.py

#The rest of the variables should be entered by the user!!

After running the Main class from app.py we receive:
            -the "INFO:root:200" if the connector was created successfully
            -the "ERROR:root:The connector was already created, please create another connector!" if the name of the 
            connector is the same with a connector already created and you need to change it in order for the app to run
            -the first 10 results of the sql query

After running the Main class from app_rest_api.py we receive:
            -"DEBUG:root:200" if the queries were sent successfully
            -the "ERROR:root:The connector was already created, please create another connector!" if the name of the 
            connector is the same with a connector already created and you need to change it in order for the app to run

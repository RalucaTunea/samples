#### Project Setup  

We need as conduit server to be in run mode.
Infos about how to set up server of conduit or how to run it, please see bde/README.md

#### Packages

[>> api](./main/java/com/conduit/sample/api) - it has REST API request (response and request)
    - response folder has responses from login, database and create connector
    - request folder has requests from login, database and create connector
    - ApiClient has - REST API methods (eg. post, get) 
                    - method for authentication - using SSLConnectionSocketFactory for certificate

[>> services](./main/java/com/conduit/sample/services) - it has entity folder and service class
    - entity folder has -> Connector class, which is connector structure used in CreateConnectorRequest class to create an object to json 
                        -> ExploreRequest, which is database response's structure, it is used in DBResponse class to create response 
                        from json format in object format
    - service class (CreateConnectorService and DBRequestService) in these services -> use Request and Response instance, which are
                                                                  necessary for call API request method (e.g post)
                                                                                   -> set Request  

[>> config](./main/java/com/conduit/sample/config) - it has AppConfig class  manages the user inputs from terminal

App class is main class - sets info from user
                        - calls commands for login, database request which is used to create connector request, and perform a sql query 

Rest class is main class - sets info from user
                         - calls commands for login, database request which is used to create connector request, and use REST request to perform sql query  

Remarks : App class differs from Rest class because it makes sql request using jdbc, and  Rest class uses REST request to perform queries           

#### Run
To configure the environment variables follow next step:
In intellij can modify using Edit Configuration for App and Rest classes, so add at VM option from configuration your values as in next example:
   -DEMAIL=email@domain.com -DPASSWORD=password -DTABLES=table1,table2 -DAUTHENTICATION=authentication -DURL=url -DUSERNAME=system -DDESCRIPTION=description -DCONNECTOR_NAME=conne_name -DNAMESPACE=namespace -DTYPE_DATABASE=database_type -DSPECIFIC_COLUMNS=4 -DIS_AUTHORIZATION=false 
After run we receive - INFO messages or WARNING messages
                      eg. 
                        INFO: Create Connector Response: HTTP/1.1 200 OK 
                      or 
                        WARNING: Connector already exist.
                        WARNING: Invalid username or password. Please contact your Admin.
                                                                             
Obs: If appear   
SLF4J: Found binding in [jar:file:/your_path/.m2/repository/org/slf4j/slf4j-log4j12/1.7.5/slf4j-log4j12-1.7.5.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/your_path/.m2/repository/org/slf4j/slf4j-jdk14/1.7.30/slf4j-jdk14-1.7.30.jar!/org/slf4j/impl/StaticLoggerBinder.class]                                                                                              
must give next command in terminal :
       rm -rf ~/.m2/repository/org/slf4j/slf4j-log4j12
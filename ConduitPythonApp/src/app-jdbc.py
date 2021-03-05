import logging
import os
from src.service.query_sql_service import QueryApi
from src.service.connector_service import ConnectorService

logging.basicConfig(level=logging.DEBUG)


class Main:
    username = os.getenv('API_USER')
    password = os.getenv('API_PASS')
    token = os.environ.get('OAUTH_TOKEN')
    conn_username = os.getenv('USER')
    conn_password = os.getenv('PASS')
    auth_type = os.getenv('AUTH_TYPE')
    name = os.getenv('CONNECTOR_NAME')
    url = os.getenv('URL')
    table_name = [os.getenv('TABLE_NAME')]
    description = ''
    connector_service = ConnectorService()

    oracle_connector = connector_service.set_connector(username, password, token, conn_username, conn_password,
                                                       auth_type, name, url, description)
    oracle_connector.gather_info_for_connector(table_name)

    sql = "SELECT`Book-Title`FROM `oracle_" + name + "`.`" + table_name[0] + "`" + " LIMIT 200"

    query = QueryApi()
    results = query.get_query_api(sql)

    assert results is not None
    expect_first_result = ('The Global Soul: Jet Lag, Shopping Malls, and the Search for Home (Vintage Departures)',)
    assert results[0] == expect_first_result
    logging.debug("Assertion was successful!")

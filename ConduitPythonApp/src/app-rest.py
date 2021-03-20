import logging
import os
from src.request.query_request import QueryRequest
from src.service.connector_service import ConnectorService
from src.service.query_service import QueryService
import json

logging.basicConfig(level=logging.DEBUG)


class Main:
    username = os.getenv('API_USER')
    password = os.getenv('API_PASS')
    token = os.environ.get('OAUTH_TOKEN')
    conn_username = os.getenv('USER')
    conn_password = os.getenv('PASS')
    auth_type = os.getenv('AUTH_TYPE')
    name = os.getenv('CONNECTOR_NAME')
    url = os.getenv('DATA_SOURCE_LOCATION')
    table_name = [os.getenv('TABLE_NAME')]
    description = ''
    connector_service = ConnectorService()

    oracle_connector = connector_service.set_connector(username, password, token, conn_username, conn_password,
                                                       auth_type, name, url, description)
    oracle_connector.gather_info_for_connector(table_name)

    sql = "SELECT`Book-Title`FROM `oracle_" + name + "`.`" + table_name[0] + "`" + " LIMIT 200"
    query = QueryRequest()
    data = query.set_query(sql)

    results = QueryService()
    list_results = results.query_response(data)

    assert results is not None
    expect_first_result = 'The Global Soul: Jet Lag, Shopping Malls, and the Search for Home (Vintage Departures)'
    assert list_results['data']['rows'][0]['Book-Title'] == expect_first_result

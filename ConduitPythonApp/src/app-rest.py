import logging
import os
from src.request.query_request import QueryRequest
from src.service.connector_service import ConnectorService
from src.service.query_service import QueryService
import json

logging.basicConfig(level=logging.DEBUG)


class Main:
    username = os.environ.get('API_USER')
    password = os.environ.get('API_PASS')
    conn_username = os.environ.get('USER')
    conn_password = os.environ.get('PASS')
    auth_type = os.environ.get('AUTH_TYPE')
    name = os.environ.get('CONNECTOR_NAME')
    url = os.environ.get('URL')
    table_name = [os.environ.get('TABLE_NAME')]
    description =''
    connector_service = ConnectorService()

    oracle_connector = connector_service.set_connector(username, password, conn_username, conn_password,
                                                       auth_type, name, url, description)
    oracle_connector.gather_info_for_connector(table_name)

    sql = "SELECT`Book-Title`FROM `oracle_" + name + "`.`" + table_name[0] + "`"+" LIMIT 10"
    query = QueryRequest()
    data = query.set_query(sql)

    results = QueryService()
    list_results = results.query_response(data)
    json_object = json.loads(list_results.text)
    logging.debug(list_results.text)

    assert results is not None
    expect_first_result = 'The Global Soul: Jet Lag, Shopping Malls, and the Search for Home (Vintage Departures)'
    assert json_object['rows'][0]['Book-Title'] == expect_first_result

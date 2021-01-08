import logging

from src.api.datasource import DataSource
from src.api.user import User
from src.api.authentication import Authentication
from src.api.oracle_connector import OracleConnector
from src.api.conduitApi import ConduitApi

import sys


def set_connector_type(connector_type, user, authentication, conduit_api, datasource):
    if connector_type.upper() == 'ORACLE':
        return OracleConnector(user, authentication, conduit_api, datasource)


class ConnectorService:

    def __init__(self):
        self.conduit_api = ConduitApi.get_instance()

    def create_datasource(self, connection_name, connection_url, description):
        datasource = DataSource(connection_name, connection_url, description)
        return datasource

    def set_connector(self, username, password, connection_username, connection_password, authentication_type, name,
                      url, description):
        user = User(username, password)
        authentication = Authentication(connection_username, connection_password, authentication_type, '')
        oracle_connector = set_connector_type('ORACLE', user, authentication, self.conduit_api,
                                              self.create_datasource(name, url, description))

        oracle_connector.login()

        if oracle_connector.get_token() is None:
            logging.error('Invalid Token')
            sys.exit(1)

        oracle_connector.set_authentication(authentication)

        if oracle_connector.get_datasource() is None:
            logging.error('Invalid DataSource')
            sys.exit(1)

        if oracle_connector.is_datasource_unique() is False:
            logging.error('The connector was already created, please create another connector!')
            sys.exit(1)

        if oracle_connector.is_datasource_valid() is False:
            logging.error('Invalid DataSource Name')
            sys.exit(1)
        return oracle_connector

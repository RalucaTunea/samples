import requests
import json
import logging
from src.api.datasource import DataSource
import src.consts.url
from src.utils.requests_utils import *

logging.basicConfig(level=logging.DEBUG)


class Connector:
    def __init__(self, user, authentication, conduitapi):
        self.user = user
        self.conduit_api = conduitapi
        self.datasource = None
        self.authentication = authentication

    def login(self):
        auth = requests.post(src.consts.url.url + '/auth',
                             json={'email': self.user.get_email(), 'password': self.user.get_password()}, verify=False)
        state_code = int(auth.status_code)
        if state_code != 200:
            logging.exception('Error with state code ' + str(state_code))
        if self.conduit_api is None:
            logging.exception('ConduitApi is None')
        self.conduit_api.set_token(json.loads(auth.text)['jwtToken'])

    def oracle_request(self):
        logging.debug("In Connector class")

    def set_authentication(self, authentication):
        self.authentication = authentication

    def get_token(self):
        return self.conduit_api.get_token()

    def is_token_valid(self):
        return self.conduit_api.token_not_none()

    def create_datasource(self, connection_name, connection_url, description):
        datasource = DataSource(connection_name, connection_url, description)
        return datasource

    def get_datasource(self):
        return self.datasource

    def is_datasource(self):
        return self.datasource is not None

    def is_datasource_valid(self):
        return self.datasource.is_valid() and self.datasource.has_less_then144_letters()

    def is_token_valid(self):
        return self.conduit_api.token_not_none()

    def is_datasource_unique(self):
        if self.is_token_valid() is True:
            datasources_name_response = get_request(src.consts.url.url + '/api/metadata/datasourcesNames', None)

        without_list_parenthesis = datasources_name_response.text[1:-1]
        list_of_selected_tables = without_list_parenthesis.split(',')
        state_code = int(datasources_name_response.status_code)
        if state_code != 200:
            logging.error('Error with state code ' + str(state_code))
        connector_name = '"{}"'.format(self.datasource.get_name())
        if connector_name in list_of_selected_tables:
            return False
        else:
            return True

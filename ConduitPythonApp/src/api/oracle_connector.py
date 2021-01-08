import logging
import request.oracle_request
from interfaces.connector import Connector
from utils.json_utils import write
from consts.url import url_root
from utils.requests_utils import post_request
import requests
import json

logging.basicConfig(level=logging.DEBUG)


class OracleConnector(Connector):

    def __init__(self, user, authentication, conduitapi, datasource):
        super().__init__(user, authentication, conduitapi)
        self.datasource = datasource

    def login(self):
        auth = requests.post(url_root + '/auth',
                             json={'email': self.user.get_email(), 'password': self.user.get_password()}, verify=False)
        state_code = int(auth.status_code)
        if state_code != 200:
            logging.error('Error with state code ' + str(state_code))
        if self.conduit_api is None:
            logging.error('ConduitApi is None')
        self.conduit_api.set_token(json.loads(auth.text)['jwtToken'])

    def oracle_request(self):
        data = request.oracle_request.OracleReq(self.user, self.authentication, self.conduit_api, self.datasource)
        data.oracle_request()
        oracle_response = post_request(url_root + '/api/metadata/explore/oracle', data)

        write('../payload/oracle.json', oracle_response.text)

    def gather_info_for_connector(self, list_selected_tables):
        data = request.oracle_request.OracleReq(self.user, self.authentication, self.conduit_api, self.datasource)
        data = data.connector_info_request(list_selected_tables)
        oracle_response = post_request(url_root + '/api/metadata/datasources', data)
        logging.debug(oracle_response.status_code)

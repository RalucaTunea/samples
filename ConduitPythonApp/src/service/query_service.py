import urllib3

from src.consts.url import url_root
from src.utils.requests_utils import *
import logging
import json

logging.basicConfig(level=logging.DEBUG)
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)


class QueryService:

    def query_response(self, data):
        query_response = post_request(url_root + '/query/execute', data)
        json_object = json.loads(query_response.text)

        if (json_object['status'] == 'Running'):
            query_result = self.query_result(json_object['queryId'])
            json_result = json.loads(query_result.text)
            while (json_result['status'] == 'Running'):
                query_result = self.query_result(json_object['queryId'])
                json_result = json.loads(query_result.text)
            return json_result

        return json_object

    def query_result(self, id):
        query_result = get_request(url_root + '/query/execute/' + id + '/result', None)
        return query_result

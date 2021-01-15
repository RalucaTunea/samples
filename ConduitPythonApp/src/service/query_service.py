import urllib3

from src.consts.url import url_root
from src.utils.requests_utils import post_request
import logging

logging.basicConfig(level=logging.DEBUG)
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)


class QueryService:

    def query_response(self, data):
        query_response = post_request(url_root + '/query/execute', data)
        return query_response

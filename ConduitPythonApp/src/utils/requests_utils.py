import logging

import requests

import sys
from api.conduitApi import ConduitApi

__all__ = [
    'post_request',
    'get_request'
]


def get_request(url, json_payload, verify=False):
    headers = ConduitApi.get_instance().get_headers()
    if headers['Authorization'] is not None:
        return requests.get(url, json=json_payload, headers=headers, verify=verify)
    else:
        logging.error('Error invalid token')
        sys.exit(1)


def post_request(url, json_payload,  verify=False):
    headers = ConduitApi.get_instance().get_headers()
    if headers['Authorization'] is not None:
        return requests.post(url, json=json_payload, headers=headers, verify=verify)
    else:
        logging.error('Error invalid token')
        sys.exit(1)
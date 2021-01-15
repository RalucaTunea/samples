import os

__all__ = [
    'connector_req_payload',
    'oracle_payload',
    'oracle_requests_payload',
    'query_request_payload'
    ]

connector_req_payload = os.path.abspath('payload/connector_request.json')
oracle_payload = os.path.abspath('payload/oracle.json')
oracle_requests_payload = os.path.abspath('payload/oracle_requests.json')
query_request_payload = os.path.abspath('payload/query_request.json')


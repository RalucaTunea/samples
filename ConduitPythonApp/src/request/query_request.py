import src.utils.json_utils
from src.consts.payload import query_request_payload


class QueryRequest:

    def set_query(self, query):
        data = src.utils.json_utils.read(query_request_payload)
        data["query"] = query
        return data

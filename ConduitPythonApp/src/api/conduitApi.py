import base64

class ConduitApi:
    __instance = None

    def __init__(self):
        """ Virtually private constructor. """
        if ConduitApi.__instance is not None:
            raise Exception("This class is a singleton!")
        else:
            ConduitApi.__instance = self
            self.token = None
            self.headers = None

    @staticmethod
    def get_instance():
        """ Static access method. """
        if ConduitApi.__instance is None:
            ConduitApi()
        return ConduitApi.__instance

    def set_token(self, token):
        self.token = token
        self.headers = {'Authorization': 'Bearer ' + token}

    def get_token(self):
        return self.token

    def get_headers(self):
        return self.headers

    def token_not_none(self):
        return self.token is not None

    def encodebase64(self, message):
        message_bytes = message.encode('ascii')
        base64_bytes = base64.b64encode(message_bytes)
        base64_message = base64_bytes.decode('ascii')
        return base64_message

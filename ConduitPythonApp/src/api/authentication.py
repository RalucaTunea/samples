class Authentication:
    def __init__(self, connection_username, connection_password, authentication_type, subscriptionId):
        self.connection_username = connection_username
        self.connection_password = connection_password
        self.authentication_type = authentication_type
        self.subscriptionId = subscriptionId

    def get_connection_username(self):
        return self.connection_username

    def get_connection_password(self):
        return self.connection_password

    def get_connection_authentication_type(self):
        return self.authentication_type

    def get_connection_subscription_id(self):
        return self.subscriptionId
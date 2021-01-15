import re

class DataSource:
    def __init__(self, name, url, description):
        self.name = name
        self.url = url
        self.description = description

    def is_valid(self):
        return bool(re.match("^[A-Za-z0-9_-]*$", self.name))

    def has_less_then144_letters(self):
        return len(self.name) < 114

    def get_name(self):
        return self.name

    def get_url(self):
        return self.url

    def get_description(self):
        return self.description



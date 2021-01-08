import json

__all__ = ['read', 'write']


def read(file_name):
    f = open(file_name, "r")
    # Reading from file
    data = json.loads(f.read())
    f.close()
    return data


def write(file_name, data):
    f = open(file_name, "w")
    #Writing in file
    f.write(data)
    f.close()
    return data

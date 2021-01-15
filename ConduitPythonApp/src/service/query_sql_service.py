import jaydebeapi
import logging
import urllib3
import os

urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)


class QueryApi:
    def __init__(self):
        self.conduit_host_domain = "localhost"
        self.conduit_endpoint_port = "10002"

    def get_query_api(self, sql, username="", password=""):
        DIR = os.getcwd() + '/lib/'

        jarfile = DIR + "conduit_utils.jar"
        endpoint = "jdbc:hive2://{}:{}/;transportMode=http;httpPath=cliservice".format(
            self.conduit_host_domain, self.conduit_endpoint_port
        )
        args = ("org.apache.hive.jdbc.HiveDriver", endpoint, [username, password])
        try:
            conn = jaydebeapi.connect(*args, jarfile)
            curs = conn.cursor()
            curs.execute(sql)
            results = curs.fetchall()
        except Exception as e:
            logging.error("Exception occured in get_query_editor()", exc_info=True)
            logging.error("Error: unable to get data", e)
        finally:
            curs.close()
            conn.close()

        return results

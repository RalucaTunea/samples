import logging

from src.consts.payload import connector_req_payload, oracle_requests_payload, oracle_payload
from src.interfaces.connector import Connector
import src.utils.json_utils


class OracleReq(Connector):

    def __init__(self, user, authentication, conduitapi, datasource):
        super().__init__(user, authentication, conduitapi)
        self.datasource = datasource

    def oracle_request(self):
        data = src.utils.json_utils.read(oracle_requests_payload)
        data['name'] = self.datasource.get_name()

        if not self.datasource.get_description():
            data['description'] = self.datasource.get_description()

        data['connectionUrl'] = self.datasource.get_url()
        data['connectionUsername'] = self.authentication.get_connection_username()
        data['connectionPassword'] = self.conduit_api.encodebase64(self.authentication.get_connection_password())

        if self.authentication.get_connection_authentication_type() != '':
            data['authenticationType'] = self.authentication.get_connection_authentication_type()

        data['subscriptionId'] = self.authentication.get_connection_subscription_id()
        return data

    def connector_info_request(self, list_selected_tables):
        data = src.utils.json_utils.read(connector_req_payload)

        data['name'] = self.datasource.get_name()
        data['database'] = self.get_fields_from_oracle_json_after_field('name')
        data['connectionUrl'] = self.datasource.get_url()
        data['connectionUsername'] = self.authentication.get_connection_username()
        data['connectionPassword'] = self.authentication.get_connection_password()
        data['description'] = self.datasource.get_description()
        data['tableNames'] = list_selected_tables
        data['authenticationType'] = self.get_fields_from_oracle_request_json_after_field(
            'authenticationType')

        dict_of_tables_column = dict()
        for table_name in list_selected_tables:
            dict_of_tables_column[table_name] = self.get_columns_after_tablename_from_oracle_response(
                table_name)
        data['tablesSchema'] = dict_of_tables_column

        dict_of_partition_column = dict()
        for table_name in list_selected_tables:
            dict_of_partition_column[table_name] = self.get_partition_column_from_oracle_response(table_name)

        data['storageLevel'] = self.set_storage_level(list_selected_tables)
        data['specificColumns']['storageLevel'] = self.set_storage_level(list_selected_tables)
        data['specificColumns']['partitionColumn'] = self.set_partition_column(list_selected_tables)
        data['specificColumns']['partitionCount'] = self.set_partition_count(list_selected_tables)
        data['specificColumns']['partitionColumnsList'] = dict_of_partition_column
        data['dataSourceConnection']['database'] = self.get_fields_from_oracle_json_after_field('name')
        data['dataSourceConnection']['connectionUrl'] = self.datasource.get_url()
        data['dataSourceConnection']['connectionUsername'] = self.authentication.get_connection_username()
        data['dataSourceConnection']['connectionPassword'] = self.conduit_api.encodebase64(
            self.authentication.get_connection_password())

        return data
    def get_fields_from_oracle_request_json_after_field(self, field):
        data = src.utils.json_utils.read(oracle_requests_payload)
        return data[field]

    def get_fields_from_oracle_json_after_field(self, field):
        data = src.utils.json_utils.read(oracle_payload)
        return data[0][field]

    def get_columns_after_tablename_from_oracle_response(self, table_name):
        data = src.utils.json_utils.read(oracle_payload)
        columns = []
        index = 0
        go = True
        while index < len(data[0]['tables']) and go:
            if data[0]['tables'][index]['name'] == table_name:
                columns = data[0]['tables'][index]['columns']
                go = False
            index += 1
        if not columns:
            logging.error('Error the table name ' + table_name + ' does not exist in table')
        return columns

    def get_partition_column_from_oracle_response(self, table_name):
        data = src.utils.json_utils.read(oracle_payload)
        columns = []
        index = 0
        go = True
        while index < len(data[0]['tables']) and go:
            if data[0]['tables'][index]['name'] == table_name:
                columns = data[0]['tables'][index]['partitionColumns']
                go = False
            index += 1
        if not columns:
            logging.error('Error the column ' + table_name + ' does not exist in table')
        return columns

    def set_partition_count(self, options):
        dict_partition_count = dict()
        for element in options:
            dict_partition_count[element] = 4
        return dict_partition_count

    def set_storage_level(self, options):
        dict_partition_count = dict()
        for element in options:
            dict_partition_count[element] = "DISK_ONLY"
        return dict_partition_count

    def set_partition_column(self, options):
        dict_partition_column = dict()
        for element in options:
            dict_partition_column[element] = ''
        return dict_partition_column
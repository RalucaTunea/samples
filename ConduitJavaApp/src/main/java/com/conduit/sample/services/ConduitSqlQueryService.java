package com.conduit.sample.services;

import com.conduit.sample.utils.Roots;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class ConduitSqlQueryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConduitSqlQueryService.class);

    public void executeSqlQuery(String sql) throws Exception {
        String USER = "system";
        String PASS = "BDE_great!23";
        String URL = Roots.databaseUrl;

        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
        } catch (ClassNotFoundException e) {
            LOGGER.warn("Can not connect to database");
            System.exit(1);
        }

        Connection con = DriverManager.getConnection(URL, USER, PASS);

        Statement stmt = con.prepareStatement(sql);
        //stmt.setMaxRows(10);  //Limit or MaxRow
        stmt.setFetchSize(10);

        ResultSet resultSet = stmt.executeQuery(sql);
        resultSet.setFetchSize(10);

        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
    }
}

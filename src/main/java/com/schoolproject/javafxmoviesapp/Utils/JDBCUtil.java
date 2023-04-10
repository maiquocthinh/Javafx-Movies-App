package com.schoolproject.javafxmoviesapp.Utils;

import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {
    public static Connection getConnecttion() throws IOException, SQLException {
        Properties prop = new Properties();
        prop.load(new FileInputStream(JDBCUtil.class.getResource("/Properties/database.properties").getPath()));

        String url = "jdbc:mySQL://" + prop.getProperty("DB_HOST") + ":" + prop.getProperty("DB_PORT") + "/" + prop.getProperty("DB_NAME") +"?allowMultiQueries=true";
        String username = prop.getProperty("DB_USER");
        String password = prop.getProperty("DB_PASS");

        return DriverManager.getConnection(url, username, password);
    }

    public static void printInfo(Connection connection) throws SQLException {
        if (connection != null) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            System.out.println(databaseMetaData.getDatabaseProductName());
            System.out.println(databaseMetaData.getDatabaseProductVersion());
        }
    }
}

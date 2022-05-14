package com.ersted.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    private static final String URL_PROPERTY = "db.url";
    private static final String USER_PROPERTY = "db.user";
    private static final String PASSWORD_PROPERTY = "db.password";
    private static final String DRIVER_PROPERTY = "db.driver";


    public static Connection getConnection(){
        return getConnectionToDataBase();
    }

    private static Connection getConnectionToDataBase() {
        Connection connection;

        try {
            Class.forName(PropertiesUtil.getProperty(DRIVER_PROPERTY));

            connection = DriverManager.getConnection(
                    PropertiesUtil.getProperty(URL_PROPERTY),
                    PropertiesUtil.getProperty(USER_PROPERTY),
                    PropertiesUtil.getProperty(PASSWORD_PROPERTY)
           );

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }
}

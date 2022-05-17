package com.ersted.util;

import java.sql.*;

public class JDBCUtil {
    private static final String URL_PROPERTY = "db.url";
    private static final String USER_PROPERTY = "db.user";
    private static final String PASSWORD_PROPERTY = "db.password";
    private static final String DRIVER_PROPERTY = "db.driver";

    private static final Connection CONNECTION;

    static {
        CONNECTION = getConnectionToDataBase();
    }

    public static Connection getConnection(){
        return CONNECTION;
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

    public static Statement getStatement(){
        Statement statement;
        try {
            statement = CONNECTION.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statement;
    }

    public static PreparedStatement getPreparedStatement(String SQL){
        PreparedStatement statement;
        try {
            statement = CONNECTION.prepareStatement(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statement;
    }
}

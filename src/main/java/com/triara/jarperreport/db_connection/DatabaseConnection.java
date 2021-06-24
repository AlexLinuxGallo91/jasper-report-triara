package com.triara.jarperreport.db_connection;

import com.triara.jarperreport.beans.ArgumentDestinationBean;
import com.triara.jarperreport.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection conn;

    public static void initDbConnection(ArgumentDestinationBean bean) throws SQLException {
        JdbcUtils.loadJdbcClass(bean);
        DatabaseConnection.conn = DriverManager.getConnection(JdbcUtils.generateJdbcUrl(bean),
                bean.getUsername(), bean.getPassword());
    }

    public static Connection getDbConnection() {
        return DatabaseConnection.conn;
    }

    public static void closeConnection() {
        try {
            if (DatabaseConnection.getDbConnection() != null) {
                DatabaseConnection.getDbConnection().close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}

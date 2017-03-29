package ge.edu.tsu.hrs.control_panel.server.dao;

import ge.edu.tsu.hrs.control_panel.server.util.HRSPropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static String databaseDriver;

    private static String databaseURL;

    private static String databaseUsername;

    private static String databasePassword;

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (databaseDriver == null) {
                initParams();
            }
            Class.forName(databaseDriver);
            connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
        } catch (Exception ex) {
            System.out.println("Sql connection problem...");
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println("Sql connection closing problem...");
        }
    }

    private static void initParams() {
        if (databaseDriver == null) {
            databaseDriver = HRSPropertiesUtil.getProperty("jdbc.driver");
            databaseURL = HRSPropertiesUtil.getProperty("jdbc.url");
            databaseUsername = HRSPropertiesUtil.getProperty("jdbc.username");
            databasePassword = HRSPropertiesUtil.getProperty("jdbc.password");
        }
    }
}

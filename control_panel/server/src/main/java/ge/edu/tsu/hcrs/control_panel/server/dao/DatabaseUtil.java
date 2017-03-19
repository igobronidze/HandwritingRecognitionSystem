package ge.edu.tsu.hcrs.control_panel.server.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {

    private static final String dbPropertyPath = "properties/db.properties";

    private static String databaseDriver;

    private static String databaseURL;

    private static String databaseUsername;

    private static String databasePassword;

    private static Connection connection;

    public static Connection getConnection(){
        try {
            File file = new File(dbPropertyPath);
            System.out.println(file.getAbsolutePath());
            initParams();
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
            try {
                Properties properties = new Properties();
                FileInputStream fis = new FileInputStream(dbPropertyPath);
                properties.load(fis);
                databaseDriver = properties.getProperty("jdbc.driver");
                databaseURL = properties.getProperty("jdbc.url");
                databaseUsername = properties.getProperty("jdbc.username");
                databasePassword = properties.getProperty("jdbc.password");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

package ge.edu.tsu.hcrs.control_panel.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final String databaseURL = "jdbc:postgresql://139.59.208.165:5432/hcrs_db";

    private static final String databaseUsername = "dev";

    private static final String databasePassword = "dev";

    private static Connection connection;

    public static Connection getConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
        } catch (Exception ex) {
            System.out.println("Sql connection problem...");
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Sql connection closing problem...");
        }
    }
}

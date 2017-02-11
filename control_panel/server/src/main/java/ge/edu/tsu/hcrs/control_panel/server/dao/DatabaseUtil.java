package ge.edu.tsu.hcrs.control_panel.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final String databaseURL = "jdbc:postgresql://localhost:5432/handwriting_recognition";

    private static final String databaseUsername = "postgres";

    private static final String databasePassword = "pass";

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

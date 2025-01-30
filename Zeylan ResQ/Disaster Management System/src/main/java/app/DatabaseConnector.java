package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {


    private static final String URL = "jdbc:mysql://localhost:3306/zeylan resq";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Unable to establish a connection to the database.");
        }
        return connection;
    }

}

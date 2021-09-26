package database;

import java.sql.*;

/**Deals with connecting/disconnecting with database and generating queries/updates to database.*/
public class JDBC {

    /**Connection object for database*/
    private static Connection conn;

    // SELECT
    public static ResultSet exQuery(String stmt) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/client_schedule", "sqlUser", "Passw0rd!");
        return conn.createStatement().executeQuery(stmt);
    }

    // INSERT, UPDATE, DELETE
    public static void exUpdate(String stmt) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/client_schedule", "sqlUser", "Passw0rd!");
        conn.createStatement().executeUpdate(stmt);
    }

    // Prepared Statement
    public static PreparedStatement pStatement(String stmt) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/client_schedule", "sqlUser", "Passw0rd!");
        return conn.prepareStatement(stmt);
    }

    public static void disconnect() throws SQLException {
        conn.close();
    }
}

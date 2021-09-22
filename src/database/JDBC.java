package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    static Connection conn;

    public static void connect() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/client_schedule", "sqlUser", "Passw0rd!");
    }

    public static void disconnect() throws SQLException {
        conn.close();
    }
}

package database;

import java.sql.*;

/**Deals with connecting/disconnecting with database and generating queries/updates to database.*/
public abstract class JDBC {

    /**Connection object for database*/
    private static Connection conn;

    // SELECT
    /** Executes SELECT queries to database.
     * @param stmt the statement string to query
     * @return the ResultSet table of results
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static ResultSet exQuery(String stmt) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/client_schedule", "sqlUser", "Catcher54#");
        return conn.createStatement().executeQuery(stmt);
    }

    // Prepared Statement
    /** Creates a prepared statement for execution.
     * @param stmt the statement to query/execute
     * @return the statement to implement
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static PreparedStatement pStatement(String stmt) throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/client_schedule", "sqlUser", "Catcher54#");
        return conn.prepareStatement(stmt);
    }

    /** Disconnects machine from Database.
     * @throws SQLException Calls SQL Database Statements.
     * */
    public static void disconnect() throws SQLException {
        conn.close();
    }
}

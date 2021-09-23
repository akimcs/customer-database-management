package database;

import java.sql.SQLException;
import java.sql.ResultSet;
import static database.JDBC.conn;

public class Query {

    private static ResultSet result;

    // TODO - remove this whole class and just use statements inside DB files.

    public static void makeQuery(String query) throws SQLException {
        if (query.toLowerCase().startsWith("select")) {
            result = conn.createStatement().executeQuery(query);
            // returns result
        }
        else if (query.toLowerCase().startsWith("delete") || query.toLowerCase().startsWith("insert") || query.toLowerCase().startsWith("update")) {
            conn.createStatement().executeUpdate(query);
            // returns nothing
        }
    }

    public static ResultSet getResult() {
        return result;
    }
}

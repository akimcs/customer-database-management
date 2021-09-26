package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Handles all database calls that access the users table.*/
public class DBuser {

    // loginController - validate user credentials
    public static boolean isValidLogin(String username, String password) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM users WHERE User_Name = ? AND Password = ?");
        stmt.setString(1, username);
        stmt.setString(2,password);
        ResultSet result = stmt.executeQuery();

        boolean isValid = result.next();

        JDBC.disconnect();
        return isValid;
    }

    // APTmodController - Returns user object to pre-select in combo box given original appointment's user id
    public static User getUser(int user_id) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM users WHERE User_ID = ?");
        stmt.setInt(1, user_id);
        ResultSet result = stmt.executeQuery();

        result.next();
        int id = result.getInt("User_ID");
        String name = result.getString("User_Name");
        String pass = result.getString("Password");

        JDBC.disconnect();
        return new User(id, name, pass);
    }

    // loginController - get user id using login username and set user id to currentUserId
    public static int getUserId(String username) throws SQLException {
        PreparedStatement stmt = JDBC.pStatement("SELECT * FROM users WHERE User_Name = ?");
        stmt.setString(1, username);
        ResultSet result = stmt.executeQuery();

        result.next();
        int id = result.getInt("User_ID");

        JDBC.disconnect();
        return id;
    }

    // APTaddController, APTmodController - Get all User objects to set combo box items
    public static ObservableList<User> getAllUsers() throws SQLException{
        ResultSet result = JDBC.exQuery("SELECT * FROM users ORDER BY User_ID");

        ObservableList<User> allUsers = FXCollections.observableArrayList();
        while (result.next()) {
            int id = result.getInt("User_ID");
            String name = result.getString("User_Name");
            String pass = result.getString("Password");
            allUsers.add(new User(id, name, pass));
        }

        JDBC.disconnect();
        return allUsers;
    }
}

package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBuser {

    // loginController - validate user credentials
    public static boolean validLogin(String username, String password) throws SQLException {
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

    public static User getUser(String username) throws SQLException{

    }

    public static ObservableList<User> getAllUsers() throws SQLException{
        JDBC.connect();

        Query.makeQuery("SELECT * FROM users");
        ResultSet result = Query.getResult();

        ObservableList<User> allUsers = FXCollections.observableArrayList();

        while (result.next()) {
            int id = result.getInt("User_ID");
            String user_name = result.getString("User_Name");
            String password = result.getString("Password");
            allUsers.add(new User(id, user_name, password));
        }

        JDBC.disconnect();
        return allUsers;
    }
}

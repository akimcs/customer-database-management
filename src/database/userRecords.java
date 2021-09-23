package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userRecords {

    public static boolean validLogin(String user, String pass) throws SQLException {
        JDBC.connect();

        Query.makeQuery("SELECT * FROM users WHERE User_Name = '" + user + "' AND Password = '" + pass + "'");
        ResultSet result = Query.getResult();

        if (result.next()) {
            return true;
        }
        return false;
    }

    public static User getUser(String username) throws SQLException {
        JDBC.connect();

        Query.makeQuery("SELECT * FROM users WHERE User_Name  = '" + username + "'");
        ResultSet result = Query.getResult();
        result.next();

        int id = result.getInt("User_ID");
        String user_name = result.getString("User_Name");
        String password = result.getString("Password");

        JDBC.disconnect();
        return new User(id, user_name, password);
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

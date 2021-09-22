package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userRecords {

    public static User getUser(String userName) throws SQLException, Exception{
        JDBC.connect();

        Query.makeQuery("SELECT * FROM users WHERE User_Name  = '" + userName + "'");
        ResultSet result=Query.getResult();
        User userResult;
        while(result.next()){
            int userid=result.getInt("User_ID");
            String userNameG=result.getString("User_Name");
            String password=result.getString("Password");
            userResult= new User(userid, userName, password);
            return userResult;
        }

        JDBC.disconnect();
        return null;
    }

    public static ObservableList<User> getAllUsers() throws SQLException, Exception{
        JDBC.connect();

        ObservableList<User> allUsers= FXCollections.observableArrayList();
        String sqlStatement="select * from users";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int userid=result.getInt("User_ID");
            String userNameG=result.getString("User_Name");
            String password=result.getString("Password");
            User userResult= new User(userid, userNameG, password);
            allUsers.add(userResult);
        }

        JDBC.disconnect();
        return allUsers;
    }
}

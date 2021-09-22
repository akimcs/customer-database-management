package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Project: DAODemo2021
 * Package: sample.DAO
 * <p>
 * User: carolyn.sher
 * Date: 9/15/2021
 * Time: 10:05 AM
 * <p>
 * Created with IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 *
 * In this class we will have the Create, Read, Update and Delete files for the users table
 */
public class UserDaoImpl {
    static boolean act;
    public static User getUser(String userName) throws SQLException, Exception{
        // type is name or phone, value is the name or the phone #
        DBConnection.makeConnection();
        String sqlStatement="select * FROM users WHERE User_Name  = '" + userName+ "'";
        //  String sqlStatement="select FROM address";
        Query.makeQuery(sqlStatement);
        User userResult;
        ResultSet result=Query.getResult();
        while(result.next()){
            int userid=result.getInt("User_ID");
            String userNameG=result.getString("User_Name");
            String password=result.getString("Password");
            userResult= new User(userid, userName, password);
            return userResult;
        }
        DBConnection.closeConnection();
        return null;
    }
    public static ObservableList<User> getAllUsers() throws SQLException, Exception{
        ObservableList<User> allUsers= FXCollections.observableArrayList();
        DBConnection.makeConnection();
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
        DBConnection.closeConnection();
        return allUsers;
    }
}

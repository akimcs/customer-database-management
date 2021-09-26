package model;

/**Describes the User object.*/
public class User {

    /**holds currently logged in user's id*/
    private static int currentUserId;
    /**holds currently logged in user's username*/
    private static String currentUserName;

    /**holds user id*/
    private int id;
    /**holds user username*/
    private String user;
    /**holds user password*/
    private String pass;

    public User(int id, String user, String pass) {
        this.id = id;
        this.user = user;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(int currentUserId) {
        User.currentUserId = currentUserId;
    }

    public static String getCurrentUserName() {
        return currentUserName;
    }

    public static void setCurrentUserName(String currentUserName) {
        User.currentUserName = currentUserName;
    }

    @Override
    public String toString() {
        return id + " - " + user;
    }
}

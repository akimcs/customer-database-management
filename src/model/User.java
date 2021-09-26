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
    private String name;

    /**Constructor for User Object
     * @param id user id
     * @param name user name
     * */
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**Gets User id
     * @return user id */
    public int getId() {
        return id;
    }

    /**Sets User id
     * @param id User id*/
    public void setId(int id) {
        this.id = id;
    }

    /**Gets User name
     * @return user name */
    public String getName() {
        return name;
    }

    /**Sets User name
     * @param name User name*/
    public void setName(String name) {
        this.name = name;
    }

    /**Gets User currentUserId
     * @return user currentUserId */
    public static int getCurrentUserId() {
        return currentUserId;
    }

    /**Sets User currentUserId
     * @param currentUserId User currentUserId*/
    public static void setCurrentUserId(int currentUserId) {
        User.currentUserId = currentUserId;
    }

    /**Gets User currentUserName
     * @return user currentUserName */
    public static String getCurrentUserName() {
        return currentUserName;
    }

    /**Sets User currentUserName
     * @param currentUserName User currentUserName*/
    public static void setCurrentUserName(String currentUserName) {
        User.currentUserName = currentUserName;
    }

    /**Gets User id + name
     * @return user id + name */
    @Override
    public String toString() {
        return id + " - " + name;
    }
}

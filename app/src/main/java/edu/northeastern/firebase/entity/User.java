package edu.northeastern.firebase.entity;

/**
 * The class for the user entity.
 *
 * @author Chen Chen
 * @author Shichang Ye
 */
public class User {
    // User token can be used as the primary key.
    private String userToken;
    private String userName;

    /**
     * Non-argument constructor for the class.
     */
    public User() {
    }

    /**
     * Constructor for the class.
     *
     * @param userToken the user token
     * @param userName  the user name
     */
    public User(String userToken, String userName) {
        this.userToken = userToken;
        this.userName = userName;
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the user token.
     *
     * @return the user token
     */
    public String getUserToken() {
        return userToken;
    }
}

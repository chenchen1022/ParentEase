package edu.northeastern.firebase.entity;

import java.util.List;

import edu.northeastern.firebase.Sticker;

/**
 * The class for the user entity.
 *
 * @author Chen Chen
 * @author Shichang Ye
 */
public class User {
    // User name needs to be unique.
    private String userName;

    // User token (unique id for the app on the phone) is used to identify which phone should receive
    // a specified message.
    private String userToken;

    // Stores the stickers of the current user.
    private List<Sticker> stickersSent;
    private List<Sticker> stickersReceived;

    /**
     * Non-argument constructor for the class.
     */
    public User() {
    }

    /**
     * Constructor for the class.
     *
     * @param userToken        the user token
     * @param userName         the user name
     * @param stickersSent     the stickers sent by the user
     * @param stickersReceived the stickers received by the user
     */
    public User(String userName, String userToken, List<Sticker> stickersSent, List<Sticker> stickersReceived) {
        this.userToken = userToken;
        this.userName = userName;
        this.stickersSent = stickersSent;
        this.stickersReceived = stickersReceived;
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

    /**
     * Gets the stickers sent by the user.
     *
     * @return the stickers sent by the user
     */
    public List<Sticker> getStickersSent() {
        return stickersSent;
    }

    /**
     * Gets the stickers received by the user.
     *
     * @return the stickers received by the user
     */
    public List<Sticker> getStickersReceived() {
        return stickersReceived;
    }
}

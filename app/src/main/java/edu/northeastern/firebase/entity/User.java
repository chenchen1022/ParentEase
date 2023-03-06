package edu.northeastern.firebase.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * The class for the user entity. To make object passing between activities with an intent possible,
 * the class implements Parcelable.
 *
 * @author Chen Chen
 * @author Manping Zhao
 * @author Shichang Ye
 */
public class User implements Parcelable {
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

    protected User(Parcel in) {
        userName = in.readString();
        userToken = in.readString();
        stickersSent = in.readArrayList(Sticker.class.getClassLoader());
        stickersReceived = in.readArrayList(Sticker.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    /**
     * Sets the user token.
     *
     * @param userToken the user token
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    /**
     * Returns a string representation of the class.
     *
     * @return a string representation of the class
     */
    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userToken='" + userToken + '\'' +
                ", stickersSent=" + stickersSent +
                ", stickersReceived=" + stickersReceived +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userToken);
        dest.writeList(stickersSent);
        dest.writeList(stickersReceived);
    }
}

package edu.northeastern.firebase.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * The class that represents a Sticker item. To make object passing between activities with an
 * intent possible, the class implements Parcelable.
 *
 * @author Manping Zhao
 * @author Chen Chen
 * @author Shichang Ye
 */
public class Sticker implements Parcelable {
    private String sender;
    private String receiver;
    private String timeStamp;
    private String stickerDes;

    /**
     * Non-argument constructor of the class.
     */
    public Sticker() {
    }

    /**
     * Constructor of the class.
     *
     * @param sender     the user who sends the sticker
     * @param receiver   the user who receive the sticker
     * @param timeStamp  the time when the sticker is sent
     * @param stickerDes the description of the sticker
     */
    public Sticker(String sender, String receiver, String timeStamp, String stickerDes) {
        this.sender = sender;
        this.receiver = receiver;
        this.timeStamp = timeStamp;
        this.stickerDes = stickerDes;
    }

    protected Sticker(Parcel in) {
        sender = in.readString();
        receiver = in.readString();
        timeStamp = in.readString();
        stickerDes = in.readString();
    }

    public static final Creator<Sticker> CREATOR = new Creator<Sticker>() {
        @Override
        public Sticker createFromParcel(Parcel in) {
            return new Sticker(in);
        }

        @Override
        public Sticker[] newArray(int size) {
            return new Sticker[size];
        }
    };

    /**
     * Gets the sender.
     *
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gets the receiver.
     *
     * @return the receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Gets the time stamp.
     *
     * @return the time stamp
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Gets the description of the sticker
     *
     * @return the description of the sticker
     */
    public String getStickerDes() {
        return stickerDes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(sender);
        parcel.writeString(receiver);
        parcel.writeString(timeStamp);
        parcel.writeString(stickerDes);
    }
}

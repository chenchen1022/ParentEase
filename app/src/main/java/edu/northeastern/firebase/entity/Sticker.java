package edu.northeastern.firebase.entity;

/**
 * The class that represents a Sticker item.
 *
 * @author Manping Zhao
 */
public class Sticker {
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


}

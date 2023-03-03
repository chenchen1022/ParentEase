package edu.northeastern.firebase;

/**
 * The class that represents a Sticker item.
 *
 * @author Manping Zhao
 */
public class Stickers implements Comparable<Stickers>{
    public String fromUser;
    public String toUser;
    public String sendTime;
    public String receivedTime;
    public String stickerDes;

    /**
     * Constructor of the class.
     *
     * @param fromUser         the user who send sticker
     * @param toUser           the user who receive sticker
     * @param sendTime         the time when the sticker is sent
     * @param receivedTime     the time when the sticker is received
     * @param stickerDes       the description of the sticker
     */
    public Stickers(String fromUser, String toUser, String sendTime, String receivedTime, String stickerDes) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.sendTime = sendTime;
        this.receivedTime = receivedTime;
        this.stickerDes = stickerDes;
    }

    /**
     * Gets the key.
     *
     * @return the key
     */
    public String getKey() {
        return stickerDes + " " + fromUser + " " + toUser + " " + sendTime;
    }

    /**
     * Gets the fromUser.
     *
     * @return the fromUser
     */
    public String getFromUser() {
        return fromUser;
    }

    /**
     * Sets the fromUser.
     */
    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    /**
     * Gets the toUser.
     *
     * @return the toUser
     */
    public String getToUser() {
        return toUser;
    }

    /**
     * Sets the toUser.
     */
    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    /**
     * Gets the sentTime.
     *
     * @return the sentTime
     */
    public String getSendTime() {
        return sendTime;
    }

    /**
     * Sets the sentTime.
     */
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * Gets the receivedTime.
     *
     * @return the receivedTime
     */
    public String getReceivedTime() {
        return receivedTime;
    }

    /**
     * Sets the receivedTime.
     */
    public void setReceivedTime(String receivedTime) {
        this.receivedTime = receivedTime;
    }

    /**
     * Gets the sticker description.
     *
     * @return the stickerDes
     */
    public String getStickerDes() {
        return stickerDes;
    }

    /**
     * Sets the sticker description.
     */
    public void setStickerDes(String stickerDes) {
        this.stickerDes = stickerDes;
    }

    /**
     * Compare the send time between this sticker to other sticker.
     *
     * @return an int, -1 when this sticker send time is less than other sticker
     *                  0 when this sticker send time is equal to other sticker
     *                  1 when this sticker send time is larger than other sticker
     */
    @Override
    public int compareTo(Stickers other) {
        return this.sendTime.compareTo(other.getSendTime());
    }
}

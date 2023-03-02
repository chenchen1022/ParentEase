package edu.northeastern.atyourservice;

public class Stickers implements Comparable<Stickers>{
    public String fromUser;
    public String toUser;
    public String sendTime;
    public String receivedTime;
    public String stickerDes;

    public Stickers(String fromUser, String toUser, String sendTime, String receivedTime, String stickerDes) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.sendTime = sendTime;
        this.receivedTime = receivedTime;
        this.stickerDes = stickerDes;
    }
    public String getKey() {
        return stickerDes + " " + fromUser + " " + toUser + " " + sendTime;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(String receivedTime) {
        this.receivedTime = receivedTime;
    }

    public String getStickerDes() {
        return stickerDes;
    }

    public void setStickerDes(String stickerDes) {
        this.stickerDes = stickerDes;
    }

    @Override
    public int compareTo(Stickers other) {
        return this.sendTime.compareTo(other.getSendTime());
    }
}

package data;

import java.util.Date;


public class Message {

    private int messsageID;
    private int userID;
    private int chatID;
    private String text;
    private Date date;

    public Message() {

    }

    public int getMesssageID() {
        return messsageID;
    }

    public void setMesssageID(int messsageId) {
        this.messsageID = messsageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getChatID() {
        return chatID;
    }

    public void setChatID(int chatID) {
        this.chatID = chatID;
    }

    public String toString() {
        return "Message{" +
                "messsageID=" + messsageID +
                ", UserID=" + userID +
                ", ChatID=" + chatID +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}

package data;

import java.util.Date;


public class Message {

    private Integer messsageID;
    private Integer userID;
    private Integer chatID;
    private String text;
    private Date date;

    public Message() {

    }

    public Integer getMesssageID() {
        return messsageID;
    }

    public void setMesssageID(Integer messsageId) {
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

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getChatID() {
        return chatID;
    }

    public void setChatID(Integer chatID) {
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

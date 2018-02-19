package data;

import java.util.Collection;
import java.util.Date;

public interface MessageDAO {
    public void addMessage(Message message);

    public void updateMessage(Message message);

    public void deleteMessage(Message message);

    public Collection getMessagesByUser(User user);

    public Collection getMessagesByChat(Chatroom chatroom);

    public void deleteMessagesByChat(Chatroom chatroom);

    public Message getMessageByID(int id);

    public Collection getMessagesByDate(Date date);
}

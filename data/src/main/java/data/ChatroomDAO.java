package data;


import java.util.Collection;

public interface ChatroomDAO {
    public void addChatroom(Chatroom chatroom);

    public void updateChatroom(Chatroom chatroom);

    public void deleteChatroom(Chatroom chatroom);

    public Collection getChatroomAll();

    public Collection getChatroomsByUser(User user);

    public Chatroom getChatroomByID(int id);

    public void deleteChatroomByID(int id);

    public Chatroom getChatroomByName(String name);
}

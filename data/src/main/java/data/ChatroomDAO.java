package data;


import java.util.Collection;

public interface ChatroomDAO {
    public void addChatroom(Chatroom chatroom);

    public void updateChatroom(Chatroom chatroom);

    public void deleteChatroom(Chatroom chatroom);

    public void deleteChatroomByID(Integer id);

    public Collection getChatroomAll();

    public Chatroom getChatroomByID(Integer id);

    public Chatroom getChatroomByName(String name);

    public Collection getChatroomsByUser(User user);

    public boolean isNull(Chatroom chatroom);
}

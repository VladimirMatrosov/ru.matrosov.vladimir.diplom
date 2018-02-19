package data;

import java.util.Collection;

public interface UserDAO {
    public void addUser(User user);

    public void updateUser(User user);

    public Collection getUsersAll();

    public User getUserByID(int id);

    public void deleteUserByID(int id);

    public User getUserByEmail(String email);

    public void deleteUser(User user);

    public Collection getUsersByChatroom(Chatroom chatroom);
}

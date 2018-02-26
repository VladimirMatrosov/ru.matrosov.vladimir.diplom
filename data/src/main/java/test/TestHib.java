package test;

import data.*;

import java.util.Collection;

public class TestHib {
    public static void main(String[] args){
        UserDAO userdao = new UserDAOImp();
        Collection<User> collection = userdao.getUsersAll();
        System.out.println(collection.toString());
        ChatroomDAO chatroomDAO = new ChatroomDAOImpl();
        Chatroom chatroom = chatroomDAO.getChatroomByID(5);

        System.out.println(chatroom.toString());
    }
}

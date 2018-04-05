package test;

import data.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestHib {
    public static void main(String[] args){
//        UserDAO userdao = new UserDAOImp();
//        Collection<User> collection = userdao.getUsersAll();
//        System.out.println(collection.toString());
     /*   ChatroomDAO chatroomDAO = new ChatroomDAOImpl();
        Chatroom chatroom = chatroomDAO.getChatroomByID(5);
        System.out.println(chatroomDAO.isNull(chatroomDAO.getChatroomByID(3)));
        System.out.println(userdao.getUserByID(2));
        System.out.println(chatroomDAO.getChatroomByID(11));
        System.out.println(chatroom.toString());

        List<User> users = (ArrayList)userdao.getUsersByChatroom(chatroomDAO.getChatroomByID(12));
        System.out.println(users.toString());

        RelationDAO relationDAO = new RelationDAOImpl();

        System.out.println(relationDAO.hasRelation(2, 11));

        User user = userdao.getUserByID(2);
        System.out.println(user.toString()); */

//        ChatroomDAOImpl chatroomDAO = new ChatroomDAOImpl();
//        Chatroom chatroom = chatroomDAO.getChatroomByID(14);
//        MessageDAO messageDAO = new MessageDAOImp();
//        List<Message> messages = (ArrayList) messageDAO.getMessagesByChat(chatroom);
//        System.out.println(messages.toString());

        Chatroom chatroom = new Chatroom();
        ChatroomDAO chatroomDAO = new ChatroomDAOImpl();
        System.out.println(chatroomDAO.isNull(chatroom));
        chatroom = chatroomDAO.getChatroomByID(4);
        System.out.println(chatroomDAO.isNull(chatroom));
    }
}

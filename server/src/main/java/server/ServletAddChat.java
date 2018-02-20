package server;

import data.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static help.Constants.FAIL;

@WebServlet(name = "ServletAddChat", urlPatterns = "/addChat")
public class ServletAddChat extends HttpServlet {

    public static final String EMAIL = "email";
    public static final String CHAT_NAME = "chatName";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ChatroomDAOImpl chatroomDAO = new ChatroomDAOImpl();
            Chatroom chatroom = new Chatroom();
            UserDAOImp userDAOImp = new UserDAOImp();
            RelationDAOImpl relationDAO = new RelationDAOImpl();
            Relation relation = new Relation();
            User user = userDAOImp.getUserByEmail(request.getParameter(EMAIL));
            chatroom.setChatroomID(null);
            chatroom.setName(request.getParameter(CHAT_NAME));
            Chatroom test = chatroomDAO.getChatroomByName(CHAT_NAME);
            if (test != null)
                response.getWriter().write(FAIL);
            else {
                chatroomDAO.addChatroom(chatroom);
                chatroom = chatroomDAO.getChatroomByName(CHAT_NAME);
                relation.setChatID(chatroom.getChatroomID());
                relation.setUserID(user.getUserID());
                relation.setRelationID(null);
                relationDAO.addRelation(relation);
                response.getWriter().write(chatroom.getChatroomID());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

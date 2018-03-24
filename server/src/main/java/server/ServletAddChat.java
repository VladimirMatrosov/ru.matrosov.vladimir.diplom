package server;

import com.google.gson.Gson;
import data.*;
import generics.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constant.Status.FAIL;
import static constant.Status.SUCCESS;

@WebServlet(name = "ServletAddChat", urlPatterns = "/addChat")
public class ServletAddChat extends HttpServlet {

    public static final String EMAIL_KEY = "email";
    public static final String CHAT_NAME_KEY = "chatName";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nameChat = request.getParameter(CHAT_NAME_KEY);
            String email = request.getParameter(EMAIL_KEY);

            UserDAO userDAO = new UserDAOImp();
            User user = userDAO.getUserByEmail(email);

            if ((!userDAO.isNull(user)) && (nameChat != null) && (!nameChat.isEmpty())) {
                Chatroom chatroom = new Chatroom(nameChat);
                ChatroomDAO chatroomDAO = new ChatroomDAOImpl();
                chatroomDAO.addChatroom(chatroom);

                Relation relation = new Relation(chatroom.getChatroomID(), user.getUserID());
                RelationDAO relationDAO = new RelationDAOImpl();
                relationDAO.addRelation(relation);

                new Response<Chatroom>(SUCCESS, chatroom).writeResponse(response);
            } else {
                new Response<Chatroom>(FAIL, null).writeResponse(response);
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

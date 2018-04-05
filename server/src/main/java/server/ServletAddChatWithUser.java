package server;

import data.*;
import generics.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constant.RequestParameters.CHAT_NAME_KEY;
import static constant.RequestParameters.EMAIL_KEY;
import static constant.RequestParameters.EMAIL_USER_KEY;
import static constant.Status.FAIL;
import static constant.Status.SUCCESS;


@WebServlet(name = "ServletAddChatWithUser", urlPatterns = "/addChatWithUser")
public class ServletAddChatWithUser extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nameChat = (String) request.getAttribute(CHAT_NAME_KEY);
            String email = request.getParameter(EMAIL_KEY);
            String email_user = request.getParameter(EMAIL_USER_KEY);

            UserDAO userDAO = new UserDAOImp();
            User user = userDAO.getUserByEmail(email);
            User userNew = userDAO.getUserByEmail(email_user);

            if ((!userDAO.isNull(user)) && (nameChat != null) && (!nameChat.isEmpty())) {
                Chatroom chatroom = new Chatroom(nameChat);
                ChatroomDAO chatroomDAO = new ChatroomDAOImpl();
                chatroomDAO.addChatroom(chatroom);

                Relation relation = new Relation(chatroom.getChatroomID(), user.getUserID());
                RelationDAO relationDAO = new RelationDAOImpl();
                relationDAO.addRelation(relation);
                Relation relationNew = new Relation(chatroom.getChatroomID(), userNew.getUserID());
                relationDAO.addRelation(relationNew);

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

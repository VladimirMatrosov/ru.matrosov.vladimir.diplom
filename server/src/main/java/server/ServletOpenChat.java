package server;

import data.*;
import generics.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static constant.RequestParameters.CHAT_NAME_KEY;
import static constant.RequestParameters.EMAIL_KEY;
import static constant.RequestParameters.EMAIL_USER_KEY;
import static constant.Status.CHAT_HAS_USERS;
import static constant.Status.FAIL;
import static constant.Status.SUCCESS;

@WebServlet(name = "ServletOpenChat", urlPatterns = "/openChat")
public class ServletOpenChat extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter(EMAIL_KEY);
            String email_user = request.getParameter(EMAIL_USER_KEY);
            if (!email.equals(email_user)) {
                UserDAO userDAO = new UserDAOImp();
                User user1 = userDAO.getUserByEmail(email);
                User user2 = userDAO.getUserByEmail(email_user);
                List<User> users = new ArrayList<>();

                ChatroomDAO chatroomDAO = new ChatroomDAOImpl();
                ArrayList<Chatroom> chatrooms1 = (ArrayList<Chatroom>) chatroomDAO.getChatroomsByUser(user1);
                Chatroom chatroom = new Chatroom();

                for (int i = 0; i < chatrooms1.size(); i++) {
                    users = (List<User>) userDAO.getUsersByChatroom(chatrooms1.get(i));
                    if (users.size() == 2) {
                        for (int j = 0; j < users.size(); j++){
                            if (users.get(j).getUserID() == user2.getUserID()) {
                                chatroom = chatrooms1.get(i);
                            }
                        }
                    }
                }

                if (!chatroomDAO.isNull(chatroom)){
                    new Response<Chatroom>(SUCCESS, chatroom).writeResponse(response);
                } else {
                    String chatName = user1.getFirstName() + " " + user1.getLastName() + " | " + user2.getFirstName() +
                            " " + user2.getLastName();
                    request.setAttribute(CHAT_NAME_KEY, chatName);
                    request.getRequestDispatcher("/addChatWithUser").forward(request,response);
                }


            } else {
                new Response<Chatroom>(CHAT_HAS_USERS, null).writeResponse(response);
            }

        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

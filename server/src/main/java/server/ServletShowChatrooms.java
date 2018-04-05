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
import java.util.Collection;
import java.util.List;

import static constant.RequestParameters.EMAIL_KEY;
import static constant.Status.FAIL;
import static constant.Status.NULL_VALUE;
import static constant.Status.SUCCESS;

@WebServlet(name = "ServletShowChatrooms", urlPatterns = "/showChatrooms")
public class ServletShowChatrooms extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String email = request.getParameter(EMAIL_KEY);
            UserDAO userDAO = new UserDAOImp();
            User user = userDAO.getUserByEmail(email);

            if (!userDAO.isNull(user)) {
                ChatroomDAO chatroomDAO = new ChatroomDAOImpl();
                List<Chatroom> chatrooms = (ArrayList<Chatroom>) chatroomDAO.getChatroomsByUser(user);
                if (!chatrooms.isEmpty()) {
                    new Response<Collection>(SUCCESS, chatrooms).writeResponse(response);
                } else {
                    new Response<Collection>(NULL_VALUE, null).writeResponse(response);
                }
            } else
                new Response<Collection>(FAIL, null).writeResponse(response);

        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

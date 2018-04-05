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

import static constant.RequestParameters.ID_CHAT_KEY;
import static constant.Status.FAIL;
import static constant.Status.SUCCESS;

@WebServlet(name = "ServletGetUsersByChat", urlPatterns = "/getUsersByChat")
public class ServletGetUsersByChat extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer idChat = Integer.parseInt(request.getParameter(ID_CHAT_KEY));
            ChatroomDAO chatroomDAO = new ChatroomDAOImpl();
            UserDAO userDAO = new UserDAOImp();
            ArrayList<User> users = (ArrayList<User>) userDAO.getUsersByChatroom(chatroomDAO.getChatroomByID(idChat));
            if (!users.isEmpty()) {
                new Response<Collection>(SUCCESS, users).writeResponse(response);
            } else {
                new Response<Collection>(FAIL, null).writeResponse(response);
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

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

import static constant.RequestParameters.EMAIL_KEY;
import static constant.RequestParameters.ID_CHAT_KEY;
import static constant.Status.*;

@WebServlet(name = "ServletLeaveChat", urlPatterns = "/leaveChat")
public class ServletLeaveChat extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter(EMAIL_KEY);
            int id_chat = Integer.parseInt(request.getParameter(ID_CHAT_KEY));

            UserDAOImp userDAOImp = new UserDAOImp();
            User user = userDAOImp.getUserByEmail(email);
            ChatroomDAOImpl chatroomDAO = new ChatroomDAOImpl();
            Chatroom chatroom = chatroomDAO.getChatroomByID(id_chat);
            if ((!userDAOImp.isNull(user))&&(!chatroomDAO.isNull(chatroom))) {
                RelationDAOImpl relationDAO = new RelationDAOImpl();
                if (relationDAO.hasRelation(user.getUserID(), chatroom.getChatroomID())) {
                    Relation relation = relationDAO.getRelationByUserAndChat(user, chatroom);
                    relationDAO.deleteRelation(relation);

                    request.getRequestDispatcher("/deleteChat").forward(request, response);
                } else
                    new Response<User>(CHAT_HAS_NOT_USER, user).writeResponse(response);
            }else
                new Response<User>(FAIL, null).writeResponse(response);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

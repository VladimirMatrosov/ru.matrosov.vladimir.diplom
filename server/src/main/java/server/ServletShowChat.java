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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static constant.Status.CHAT_HAS_NOT_USER;
import static constant.Status.FAIL;
import static constant.Status.SUCCESS;

@WebServlet(name = "ServletShowChat", urlPatterns = "/showChat")
public class ServletShowChat extends HttpServlet {

    public static final String ID_CHAT = "idChat";
    public static final String EMAIL = "email";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int chat_id = Integer.parseInt(request.getParameter(ID_CHAT));
            String email = request.getParameter(EMAIL);

            ChatroomDAOImpl chatroomDAO = new ChatroomDAOImpl();
            Chatroom chat = chatroomDAO.getChatroomByID(chat_id);
            UserDAO userDAO = new UserDAOImp();
            RelationDAO relationDAO = new RelationDAOImpl();
            if ((!userDAO.isNull(userDAO.getUserByEmail(email))) && (!chatroomDAO.isNull(chat))) {
                if (relationDAO.hasRelation(userDAO.getUserByEmail(email).getUserID(), chat.getChatroomID())) {
                    MessageDAO messageDAO = new MessageDAOImp();
                    List<Message> messages = (ArrayList)messageDAO.getMessagesByChat(chat);

                    new Response<Collection>(SUCCESS, messages).writeResponse(response);
                }else
                    new Response<Collection>(CHAT_HAS_NOT_USER, null).writeResponse(response);
            } else
                new Response<Collection>(FAIL, null).writeResponse(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

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
import java.util.List;

import static constant.Status.CHAT_HAS_USERS;
import static constant.Status.FAIL;
import static constant.Status.SUCCESS;

@WebServlet(name = "ServletDeleteChat", urlPatterns = "/deleteChat")
public class ServletDeleteChat extends HttpServlet {

    public static final String ID_CHAT = "idChat";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer id_chat = Integer.parseInt(request.getParameter(ID_CHAT));

            ChatroomDAOImpl chatroomDAO = new ChatroomDAOImpl();
            Chatroom chatroom = chatroomDAO.getChatroomByID(id_chat);

            if (!chatroomDAO.isNull(chatroom)) {
                UserDAOImp userDAOImp = new UserDAOImp();
                List<User> users = (ArrayList) userDAOImp.getUsersByChatroom(chatroom);
                if (users.size() == 0) {
                    MessageDAO messageDAO = new MessageDAOImp();
                    messageDAO.deleteMessagesByChat(chatroom);
                    chatroomDAO.deleteChatroom(chatroom);

                    new Response<>(SUCCESS, null).writeResponse(response);
                } else
                    new Response<>(CHAT_HAS_USERS, null).writeResponse(response);
            } else
                new Response<>(FAIL, null).writeResponse(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

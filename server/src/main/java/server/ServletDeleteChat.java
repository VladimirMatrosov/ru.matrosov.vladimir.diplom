package server;

import data.Chatroom;
import data.ChatroomDAOImpl;
import data.UserDAOImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static constant.Status.SUCCESS;

@WebServlet(name = "ServletDeleteChat", urlPatterns = "/deleteChat")
public class ServletDeleteChat extends HttpServlet {

    public static final String NAME_CHAT = "nameChat";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ChatroomDAOImpl chatroomDAO = new ChatroomDAOImpl();
            Chatroom chatroom = chatroomDAO.getChatroomByName(request.getParameter(NAME_CHAT));
            UserDAOImp userDAOImp = new UserDAOImp();
            List users = new ArrayList<>();
            users = (ArrayList) userDAOImp.getUsersByChatroom(chatroom);
            if (users == null) {
                chatroomDAO.deleteChatroom(chatroom);
                response.getWriter().write(SUCCESS);
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

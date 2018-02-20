package server;

import data.Chatroom;
import data.ChatroomDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static help.Constants.FAIL;

@WebServlet(name = "ServletShowChat", urlPatterns = "/showChat")
public class ServletShowChat extends HttpServlet {

    public static final String NAME_CHAT = "nameChat";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ChatroomDAOImpl chatroomDAO = new ChatroomDAOImpl();
            Chatroom chat = chatroomDAO.getChatroomByName(NAME_CHAT);
            if (chat != null)
                response.getWriter().write(chat.getChatroomID());
            else
                response.getWriter().write(FAIL);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

package server;

import data.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@WebServlet(name = "ServletSendMessage", urlPatterns = "/sendMessage")
public class ServletSendMessage extends HttpServlet {

    public static final String EMAIL = "email";
    public static final String NAME_CHAT = "nameChat";
    public static final String TEXT = "text";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserDAOImp userDAOImp = new UserDAOImp();
            ChatroomDAOImpl chatroomDAO = new ChatroomDAOImpl();
            MessageDAOImp messageDAOImp = new MessageDAOImp();
            User user = userDAOImp.getUserByEmail(request.getParameter(EMAIL));
            Chatroom chatroom = chatroomDAO.getChatroomByName(request.getParameter(NAME_CHAT));
            Message message = new Message();
            message.setMesssageID(null);
            message.setChatroomID(chatroom.getChatroomID());
            message.setUserID(user.getUserID());
            message.setText(request.getParameter(TEXT));
            Date date = Calendar.getInstance().getTime();
            message.setDate(date);
            messageDAOImp.addMessage(message);
            response.getWriter().write(chatroom.getChatroomID());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

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
import java.util.Calendar;
import java.util.Date;

import static constant.RequestParameters.EMAIL_KEY;
import static constant.RequestParameters.ID_CHAT_KEY;
import static constant.RequestParameters.TEXT_KEY;
import static constant.Status.FAIL;
import static constant.Status.NULL_VALUE;
import static constant.Status.SUCCESS;

@WebServlet(name = "ServletSendMessage", urlPatterns = "/sendMessage")
public class ServletSendMessage extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter(EMAIL_KEY);
            String text = request.getParameter(TEXT_KEY);
            int chat_id = Integer.parseInt(request.getParameter(ID_CHAT_KEY));

            UserDAOImp userDAOImp = new UserDAOImp();
            User user = userDAOImp.getUserByEmail(email);
            ChatroomDAOImpl chatroomDAO = new ChatroomDAOImpl();
            Chatroom chatroom = chatroomDAO.getChatroomByID(chat_id);
            RelationDAO relationDAO = new RelationDAOImpl();
            if ((!userDAOImp.isNull(user)) && (!chatroomDAO.isNull(chatroom)) &&
                    (relationDAO.hasRelation(user.getUserID(), chatroom.getChatroomID()))) {
                if ((text != null) && (!text.isEmpty())) {
                    MessageDAOImp messageDAOImp = new MessageDAOImp();
                    Message message = new Message();
                    message.setChatroomID(chatroom.getChatroomID());
                    message.setUserID(user.getUserID());
                    message.setText(text);
                    Date date = Calendar.getInstance().getTime();
                    message.setDate(date);
                    messageDAOImp.addMessage(message);

                    new Response<Chatroom>(SUCCESS, chatroom).writeResponse(response);
                } else
                    new Response<Chatroom>(NULL_VALUE, null).writeResponse(response);
            }else
                new Response<Chatroom>(FAIL, null).writeResponse(response);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

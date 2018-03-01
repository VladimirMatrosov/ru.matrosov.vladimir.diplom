package server;

import com.google.gson.Gson;
import data.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import static constant.Status.FAIL;
import static constant.Status.NULL_VALUE;
import static constant.Status.SUCCESS;

@WebServlet(name = "ServletSendMessage", urlPatterns = "/sendMessage")
public class ServletSendMessage extends HttpServlet {

    public static final String EMAIL = "email";
    public static final String ID_CHAT = "idChat";
    public static final String TEXT = "text";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter(EMAIL);
            String text = request.getParameter(TEXT);
            int chat_id = Integer.parseInt(request.getParameter(ID_CHAT));

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
                    writeResponse(new SendMessageResponse(SUCCESS, chatroom), response);
                } else
                    writeResponse(new SendMessageResponse(NULL_VALUE, null), response);
            }else
                writeResponse(new SendMessageResponse(FAIL, null), response);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void writeResponse(SendMessageResponse sendMessageResponse, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(sendMessageResponse);
        response.getWriter().write(str);
    }

    public class SendMessageResponse {
        int status;
        Chatroom chatroom;

        public SendMessageResponse(int status, Chatroom chatroom) {
            this.status = status;
            this.chatroom = chatroom;
        }
    }
}

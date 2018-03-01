package server;

import com.google.gson.Gson;
import data.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                    writeResponse(new ShowChatResponse(SUCCESS, chat), response);
                }else
                    writeResponse(new ShowChatResponse(CHAT_HAS_NOT_USER, null), response);
            } else
                writeResponse(new ShowChatResponse(FAIL, null), response);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void writeResponse(ShowChatResponse showChatResponse, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(showChatResponse);
        response.getWriter().write(str);
    }

    public class ShowChatResponse {
        int status;
        Chatroom chatroom;

        public ShowChatResponse(int status, Chatroom chatroom) {
            this.status = status;
            this.chatroom = chatroom;
        }
    }
}

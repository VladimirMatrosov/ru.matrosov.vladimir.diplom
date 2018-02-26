package server;

import com.google.gson.Gson;
import data.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constant.Status.FAIL;
import static constant.Status.SUCCESS;

@WebServlet(name = "ServletAddChat", urlPatterns = "/addChat")
public class ServletAddChat extends HttpServlet {

    public static final String EMAIL_KEY = "email";
    public static final String CHAT_NAME_KEY = "chatName";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nameChat = request.getParameter(CHAT_NAME_KEY);
            String email = request.getParameter(EMAIL_KEY);

            UserDAO userDAO = new UserDAOImp();
            User user = userDAO.getUserByEmail(email);

            if ((user != null) && (nameChat != null) && (!nameChat.isEmpty())) {
                Chatroom chatroom = new Chatroom(nameChat);
                ChatroomDAO chatroomDAO = new ChatroomDAOImpl();
                chatroomDAO.addChatroom(chatroom);

                Relation relation = new Relation(chatroom.getChatroomID(), user.getUserID());
                RelationDAO relationDAO = new RelationDAOImpl();
                relationDAO.addRelation(relation);

                writeResponse(new AddChatResponse(SUCCESS, chatroom),response);
            } else {
                writeResponse(new AddChatResponse(FAIL, null),response);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void writeResponse(AddChatResponse addChatResponse, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(addChatResponse);
        response.getWriter().write(str);
    }

    public class AddChatResponse {
        int status;
        Chatroom chatroom;

        public AddChatResponse(int status, Chatroom chatroom) {
            this.status = status;
            this.chatroom = chatroom;
        }
    }


}

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

@WebServlet(name = "ServletAddUserToChat", urlPatterns = "/addUserToChat")
public class ServletAddUserToChat extends HttpServlet {

    public static final String ID_CHAT_KEY = "idChat";
    public static final String EMAIL_KEY = "email";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer chatId = Integer.parseInt(request.getParameter(ID_CHAT_KEY));
            String email = request.getParameter(EMAIL_KEY);

            UserDAO userDAOImp = new UserDAOImp();
            User user = userDAOImp.getUserByEmail(email);
            ChatroomDAO chatroomDAOImp = new ChatroomDAOImpl();
            Chatroom chatroom = chatroomDAOImp.getChatroomByID(chatId);
            if ((!chatroomDAOImp.isNull(chatroom)) && (!userDAOImp.isNull(user))
                    && (new RelationDAOImpl().hasRelation(user.getUserID(), chatroom.getChatroomID()) == false)) {
                RelationDAO relationDAOImp = new RelationDAOImpl();
                Relation relation = new Relation(chatroom.getChatroomID(), user.getUserID());
                relationDAOImp.addRelation(relation);

                writeResponse(new AddUserToChatResponse(SUCCESS, chatroom), response);
            } else {
                writeResponse(new AddUserToChatResponse(FAIL, null), response);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void writeResponse(AddUserToChatResponse addUserToChatResponse, HttpServletResponse response)
            throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(addUserToChatResponse);
        response.getWriter().write(str);
    }

    public class AddUserToChatResponse {
        int status;
        Chatroom chatroom;

        public AddUserToChatResponse(int status, Chatroom chatroom) {
            this.status = status;
            this.chatroom = chatroom;
        }
    }
}

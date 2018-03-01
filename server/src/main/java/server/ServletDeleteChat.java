package server;

import com.google.gson.Gson;
import data.*;

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
                    writeResponse(new DeleteChatResponse(SUCCESS), response);
                } else
                    writeResponse(new DeleteChatResponse(CHAT_HAS_USERS), response);
            } else
                writeResponse(new DeleteChatResponse(FAIL), response);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void writeResponse(DeleteChatResponse deleteChatResponse, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(deleteChatResponse);
        response.getWriter().write(str);
    }

    public class DeleteChatResponse {
        int status;

        public DeleteChatResponse(int status) {
            this.status = status;
        }
    }
}

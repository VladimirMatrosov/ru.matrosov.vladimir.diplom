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

@WebServlet(name = "ServletLeaveChat", urlPatterns = "/leaveChat")
public class ServletLeaveChat extends HttpServlet {

    public static final String EMAIL = "email";
    public static final String ID_CHAT = "idChat";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter(EMAIL);
            int id_chat = Integer.parseInt(request.getParameter(ID_CHAT));

            UserDAOImp userDAOImp = new UserDAOImp();
            User user = userDAOImp.getUserByEmail(email);
            ChatroomDAOImpl chatroomDAO = new ChatroomDAOImpl();
            Chatroom chatroom = chatroomDAO.getChatroomByID(id_chat);
            if ((!userDAOImp.isNull(user))&&(!chatroomDAO.isNull(chatroom))) {
                RelationDAOImpl relationDAO = new RelationDAOImpl();
                if (relationDAO.hasRelation(user.getUserID(), chatroom.getChatroomID())) {
                    Relation relation = relationDAO.getRelationByUserAndChat(user, chatroom);
                    relationDAO.deleteRelation(relation);
                    writeResponse(new LeaveChatResponse(SUCCESS, user),response);
                } else
                    writeResponse(new LeaveChatResponse(CHAT_HAS_NOT_USER, user),response);
            }else
                writeResponse(new LeaveChatResponse(FAIL,null), response);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void writeResponse(LeaveChatResponse leaveChatResponse, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(leaveChatResponse);
        response.getWriter().write(str);
    }

    public class LeaveChatResponse{
        int status;
        User user;

        public LeaveChatResponse(int status, User user) {
            this.status = status;
            this.user = user;
        }
    }
}

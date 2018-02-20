package server;

import data.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletAddUserToChat", urlPatterns = "/addUserToChat")
public class ServletAddUserToChat extends HttpServlet {

    public static final String NAME_CHAT = "nameChat";
    public static final String EMAIL = "email";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            UserDAOImp userDAOImp = new UserDAOImp();
            ChatroomDAOImpl chatroomDAOImp = new ChatroomDAOImpl();
            RelationDAOImpl relationDAOImp = new RelationDAOImpl();
            Chatroom chatroom = chatroomDAOImp.getChatroomByName(request.getParameter(NAME_CHAT));
            User user = userDAOImp.getUserByEmail(request.getParameter(EMAIL));
            Relation relation = new Relation();
            relation.setRelationID(null);
            relation.setUserID(user.getUserID());
            relation.setChatID(chatroom.getChatroomID());
            relationDAOImp.addRelation(relation);
            response.getWriter().write(chatroom.getChatroomID());

        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

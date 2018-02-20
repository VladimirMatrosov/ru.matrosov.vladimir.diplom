package server;

import data.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static help.Constants.SUCCESS;

@WebServlet(name = "ServletLeaveChat", urlPatterns = "/leaveChat")
public class ServletLeaveChat extends HttpServlet {

    public static final String EMAIL = "email";
    public static final String NAME_CHAT = "nameChat";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            UserDAOImp userDAOImp = new UserDAOImp();
            User user = userDAOImp.getUserByEmail(request.getParameter(EMAIL));
            ChatroomDAOImpl chatroomDAO = new ChatroomDAOImpl();
            Chatroom chatroom = chatroomDAO.getChatroomByName(request.getParameter(NAME_CHAT));
            RelationDAOImpl relationDAO = new RelationDAOImpl();
            Relation relation = relationDAO.getRelationByUserAndChat(user, chatroom);
            relationDAO.deleteRelation(relation);
            response.getWriter().write(SUCCESS);

        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

package server;

import com.google.gson.Gson;
import data.*;
import generics.Response;
import generics.ShowMessageResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static constant.RequestParameters.EMAIL_KEY;
import static constant.RequestParameters.ID_CHAT_KEY;
import static constant.Status.*;

@WebServlet(name = "ServletShowChat", urlPatterns = "/showChat")
public class ServletShowChat extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int chat_id = Integer.parseInt(request.getParameter(ID_CHAT_KEY));
            String email = request.getParameter(EMAIL_KEY);

            ChatroomDAOImpl chatroomDAO = new ChatroomDAOImpl();
            Chatroom chat = chatroomDAO.getChatroomByID(chat_id);
            UserDAO userDAO = new UserDAOImp();
            RelationDAO relationDAO = new RelationDAOImpl();
            if ((!userDAO.isNull(userDAO.getUserByEmail(email))) && (!chatroomDAO.isNull(chat))) {
                if (relationDAO.hasRelation(userDAO.getUserByEmail(email).getUserID(), chat.getChatroomID())) {
                    MessageDAO messageDAO = new MessageDAOImp();
                    List<Message> messages = (ArrayList) messageDAO.getMessagesByChat(chat);
                    if (!messages.isEmpty()) {
                        List<User> users = new ArrayList<>();
                        for (int i = 0; i < messages.size(); i++) {
                            int id = messages.get(i).getUserID();
                            User user = userDAO.getUserByID(id);
                            if (userDAO.isNull(user))
                                users.add(null);
                            else
                                users.add(user);
                        }

                        new ShowMessageResponse(SUCCESS, users, messages).writeResponse(response);
                    } else {
                        new ShowMessageResponse(NULL_VALUE, null, null).writeResponse(response);
                    }
                } else
                    new ShowMessageResponse(CHAT_HAS_NOT_USER, null, null).writeResponse(response);
            } else
                new ShowMessageResponse(FAIL, null, null).writeResponse(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

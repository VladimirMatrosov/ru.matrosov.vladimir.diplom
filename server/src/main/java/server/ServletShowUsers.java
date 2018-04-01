package server;

import com.sun.net.httpserver.Authenticator;
import data.User;
import data.UserDAO;
import data.UserDAOImp;
import generics.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static constant.Status.SUCCESS;
import static constant.Status.SYSTEM_HAS_NOT_USERS;

@WebServlet(name = "ServletShowUsers", urlPatterns = "/showUsers")
public class ServletShowUsers extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAOImp();
        List<User> users = (ArrayList) userDAO.getUsersAll();
        if (!users.isEmpty()) {
            new Response<Collection>(SUCCESS, users).writeResponse(response);
        }else
            new Response<Collection>(SYSTEM_HAS_NOT_USERS, null).writeResponse(response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

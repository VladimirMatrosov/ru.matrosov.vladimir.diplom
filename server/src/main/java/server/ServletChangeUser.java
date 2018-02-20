package server;

import data.User;
import data.UserDAOImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletChangeUser", urlPatterns = "/changeUser")
public class ServletChangeUser extends HttpServlet {

    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String NEW_EMAIL = "newEmail";
    public static final String POST = "post";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            UserDAOImp userDAOImp = new UserDAOImp();
            User user = userDAOImp.getUserByEmail(request.getParameter(EMAIL));
            user.setFirstName(request.getParameter(FIRST_NAME));
            user.setLastName(request.getParameter(LAST_NAME));
            user.setEmail(request.getParameter(NEW_EMAIL));
            user.setPost(request.getParameter(POST));
            userDAOImp.updateUser(user);
            response.getWriter().write(user.getUserID());
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

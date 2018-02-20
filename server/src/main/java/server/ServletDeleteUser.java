package server;

import data.User;
import data.UserDAOImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static help.Constants.SUCCESS;

@WebServlet(name = "ServletDeleteUser", urlPatterns = "/deleteUser")
public class ServletDeleteUser extends HttpServlet {

    public static final String EMAIL = "email";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            UserDAOImp userDAOImp = new UserDAOImp();
            User user = userDAOImp.getUserByEmail(request.getParameter(EMAIL));
            userDAOImp.deleteUser(user);
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

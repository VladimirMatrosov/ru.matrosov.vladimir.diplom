package server;

import data.User;
import data.UserDAOImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static help.Constants.FAIL;
import static help.Constants.PASSWORD_NOT_MATCH;

@WebServlet(name = "ServletChangePassword", urlPatterns = "/changePassword")
public class ServletChangePassword extends HttpServlet {

    public static final String EMAIL = "email";
    public static final String PASSWORD_1 = "password1";
    public static final String PASSWORD_2 = "password2";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            UserDAOImp userDAOImp = new UserDAOImp();
            User user = userDAOImp.getUserByEmail(request.getParameter(EMAIL));
            if (request.getParameter(PASSWORD_1).equals(request.getParameter(PASSWORD_2)))
                if (!(request.getParameter(PASSWORD_1).equals(user.getPassword()))) {
                    user.setPassword(request.getParameter(PASSWORD_1));
                    userDAOImp.updateUser(user);
                    response.getWriter().write(user.getUserID());
                }
                else
                    response.getWriter().write(FAIL);
            else
                response.getWriter().write(PASSWORD_NOT_MATCH);
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

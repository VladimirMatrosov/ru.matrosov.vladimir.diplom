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
import static help.Constants.SUCCESS;

@WebServlet(name = "ServletRegistration", urlPatterns = "/registration")
public class ServletRegistration extends HttpServlet {
    public static final String PASSWORD_1 = "password1";
    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String EMAIL = "email";
    public static final String POST = "post";
    public static final String PASSWORD_2 = "password2";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = new User();
            UserDAOImp userDAOImp = new UserDAOImp();
            user.setUserID(null);
            user.setFirstName(request.getParameter(FIRSTNAME));
            user.setLastName(request.getParameter(LASTNAME));
            user.setEmail(request.getParameter(EMAIL));
            user.setPost(request.getParameter(request.getParameter(POST)));
            if (request.getParameter(PASSWORD_1).equals(request.getParameter(PASSWORD_2)))
                user.setPassword(request.getParameter(PASSWORD_1));
            else
                response.getWriter().write(PASSWORD_NOT_MATCH);
            User test = userDAOImp.getUserByEmail(user.getEmail());
            if (test != null)
                response.getWriter().println(FAIL);
            else {
                userDAOImp.addUser(user);
                test = userDAOImp.getUserByEmail(EMAIL);
                response.getWriter().write(test.getUserID());

            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

package server;

import data.UserDAOImp;
import data.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static help.Constants.PASSWORD_NOT_MATCH;
import static help.Constants.SUCCESS;

@WebServlet(name = "ServletAutorization", urlPatterns = "/autorization")
public class ServletAutorization extends HttpServlet {

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            UserDAOImp userDAOImp = new UserDAOImp();
            User user = userDAOImp.getUserByEmail(request.getParameter(EMAIL));
            if (user.getPassword().equals(request.getParameter(PASSWORD))) {
                response.getWriter().write(SUCCESS);
            } else
                response.getWriter().write(PASSWORD_NOT_MATCH);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

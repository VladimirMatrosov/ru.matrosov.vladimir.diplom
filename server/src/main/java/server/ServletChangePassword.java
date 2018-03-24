package server;

import com.google.gson.Gson;
import data.User;
import data.UserDAOImp;
import generics.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constant.Status.FAIL;
import static constant.Status.PASSWORD_NOT_MATCH;
import static constant.Status.SUCCESS;

@WebServlet(name = "ServletChangePassword", urlPatterns = "/changePassword")
public class ServletChangePassword extends HttpServlet {

    public static final String EMAIL = "email";
    public static final String PASSWORD_1 = "password1";
    public static final String PASSWORD_2 = "password2";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter(EMAIL);
            String password1 = request.getParameter(PASSWORD_1);
            String password2 = request.getParameter(PASSWORD_2);

            UserDAOImp userDAOImp = new UserDAOImp();
            User user = userDAOImp.getUserByEmail(email);
            if ((!userDAOImp.isNull(user))&&(password1 != null)&&(!password1.isEmpty())&&
                    (password2 != null)&&(!password2.isEmpty())) {
                if (password1.equals(password2)) {
                    user.setPassword(password1);
                    userDAOImp.updateUser(user);
                    new Response<User>(SUCCESS, user).writeResponse(response);
                } else
                    new Response<User>(PASSWORD_NOT_MATCH, null).writeResponse(response);
            } else
                new Response<User>(FAIL, null).writeResponse(response);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

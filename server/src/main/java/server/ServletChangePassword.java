package server;

import com.google.gson.Gson;
import data.User;
import data.UserDAOImp;

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
                    writeResponse(new ChangePasswordResponse(SUCCESS, user), response);
                } else
                    writeResponse(new ChangePasswordResponse(PASSWORD_NOT_MATCH, null), response);
            } else
                writeResponse(new ChangePasswordResponse(FAIL, null), response);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void writeResponse(ChangePasswordResponse changePasswordResponse, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(changePasswordResponse);
        response.getWriter().write(str);
    }

    public class ChangePasswordResponse {
        int status;
        User user;

        public ChangePasswordResponse(int status, User user) {
            this.status = status;
            this.user = user;
        }
    }
}

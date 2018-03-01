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

import static constant.Status.*;

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
            String first_name = request.getParameter(FIRSTNAME);
            String last_name = request.getParameter(LASTNAME);
            String email = request.getParameter(EMAIL);
            String post = request.getParameter(POST);
            String password1 = request.getParameter(PASSWORD_1);
            String password2 = request.getParameter(PASSWORD_2);

            UserDAOImp userDAOImp = new UserDAOImp();
            if ((email != null) && (!email.isEmpty()) && (userDAOImp.isNull(userDAOImp.getUserByEmail(email)))) {
                User user = new User();
                user.setEmail(email);
                if ((password1 != null) && (!password1.isEmpty()) && (password2 != null) &&
                        (!password2.isEmpty()) && (password1.equals(password2))) {
                    user.setPassword(password1);
                    if ((first_name != null) && (!first_name.isEmpty()))
                        user.setFirstName(first_name);
                    if ((last_name != null) && (!last_name.isEmpty()))
                        user.setLastName(last_name);
                    if ((post != null) && (!post.isEmpty()))
                        user.setPost(post);
                    if ((user.getFirstName() != null) && (user.getPost() != null)) {
                        userDAOImp.addUser(user);
                        writeResponse(new RegistrationResponse(SUCCESS, user), response);
                    } else
                        writeResponse(new RegistrationResponse(NULL_VALUE, null), response);
                }else
                    writeResponse(new RegistrationResponse(PASSWORD_NOT_MATCH, null), response);

            } else
                writeResponse(new RegistrationResponse(FAIL, null), response);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void writeResponse(RegistrationResponse registrationResponse, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(registrationResponse);
        response.getWriter().write(str);
    }

    public class RegistrationResponse {
        int status;
        User user;

        public RegistrationResponse(int status, User user) {
            this.status = status;
            this.user = user;
        }
    }
}

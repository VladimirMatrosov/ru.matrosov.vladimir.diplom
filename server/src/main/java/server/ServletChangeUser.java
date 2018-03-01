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
import static constant.Status.SUCCESS;

@WebServlet(name = "ServletChangeUser", urlPatterns = "/changeUser")
public class ServletChangeUser extends HttpServlet {

    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String NEW_EMAIL = "newEmail";
    public static final String POST = "post";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter(EMAIL);
            String first_name = request.getParameter(FIRST_NAME);
            String last_name = request.getParameter(LAST_NAME);
            String new_email = request.getParameter(NEW_EMAIL);
            String post = request.getParameter(POST);

            UserDAOImp userDAOImp = new UserDAOImp();
            User user = userDAOImp.getUserByEmail(email);
            if (!userDAOImp.isNull(user)) {
                if (user.getFirstName() != null) {
                    if ((!user.getFirstName().equals(first_name)) && (first_name != null) && (!first_name.isEmpty()))
                        user.setFirstName(first_name);
                } else
                    user.setFirstName(first_name);

                if (user.getLastName() != null) {
                    if ((!user.getLastName().equals(last_name)) && (last_name != null) && (!last_name.isEmpty()))
                        user.setLastName(last_name);
                } else
                    user.setLastName(last_name);
                if (user.getEmail() != null) {
                    if ((!user.getEmail().equals(new_email)) && (new_email != null) && (!new_email.isEmpty()))
                        user.setEmail(new_email);
                } else
                    user.setEmail(email);
                if (user.getPost() != null) {
                    if ((!user.getPost().equals(post)) && (post != null) && (!post.isEmpty()))
                        user.setPost(post);
                } else
                    user.setPost(post);
                userDAOImp.updateUser(user);
                writeResponse(new ChangeUserResponse(SUCCESS, user), response);
            } else
                writeResponse(new ChangeUserResponse(FAIL, null), response);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void writeResponse(ChangeUserResponse changeUserResponse, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(changeUserResponse);
        response.getWriter().write(str);
    }

    public class ChangeUserResponse {
        int status;
        User user;

        public ChangeUserResponse(int status, User user) {
            this.status = status;
            this.user = user;
        }
    }
}

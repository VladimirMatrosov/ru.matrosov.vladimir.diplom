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

import static constant.RequestParameters.*;
import static constant.Status.FAIL;
import static constant.Status.SUCCESS;

@WebServlet(name = "ServletChangeUser", urlPatterns = "/changeUser")
public class ServletChangeUser extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter(EMAIL_KEY);
            String first_name = request.getParameter(FIRST_NAME_KEY);
            String last_name = request.getParameter(LAST_NAME_KEY);
            String new_email = request.getParameter(NEW_EMAIL_KEY);
            String post = request.getParameter(POST_KEY);

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
                new Response<User>(SUCCESS, user).writeResponse(response);
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

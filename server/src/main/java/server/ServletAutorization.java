package server;

import com.google.gson.Gson;
import data.UserDAOImp;
import data.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constant.Status.FAIL;
import static constant.Status.PASSWORD_NOT_MATCH;
import static constant.Status.SUCCESS;

@WebServlet(name = "ServletAutorization", urlPatterns = "/autorization")
public class ServletAutorization extends HttpServlet {

    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter(EMAIL);
            String password = request.getParameter(PASSWORD);

            UserDAOImp userDAOImp = new UserDAOImp();
            User user = userDAOImp.getUserByEmail(email);
            if (user.getUserID() != null){
                if(user.getPassword().equals(password)){
                    writeResponse(new AutorizationResponse(SUCCESS, user), response);
                }else {
                    writeResponse(new AutorizationResponse(PASSWORD_NOT_MATCH, null), response);
                }
            }else{
                writeResponse(new AutorizationResponse(FAIL, null), response);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void writeResponse(AutorizationResponse autorizationResponse, HttpServletResponse response)
            throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(autorizationResponse);
        response.getWriter().write(str);
    }

    public class AutorizationResponse {
        int status;
        User user;

        public AutorizationResponse(int status, User user) {
            this.status = status;
            this.user = user;
        }
    }
}

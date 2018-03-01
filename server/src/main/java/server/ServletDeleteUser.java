package server;

import com.google.gson.Gson;
import data.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static constant.Status.FAIL;
import static constant.Status.SUCCESS;

@WebServlet(name = "ServletDeleteUser", urlPatterns = "/deleteUser")
public class ServletDeleteUser extends HttpServlet {

    public static final String EMAIL = "email";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String email = request.getParameter(EMAIL);

            UserDAOImp userDAOImp = new UserDAOImp();
            User user = userDAOImp.getUserByEmail(email);
            if (!userDAOImp.isNull(user)) {
                RelationDAO relationDAO = new RelationDAOImpl();
                List<Relation> relations = (ArrayList)relationDAO.getRelationsByUser(user);
                if (relations.size() != 0){
                 for (int i=0; i<relations.size(); i++){
                     Relation relation = relations.get(i);
                     relationDAO.deleteRelation(relation);
                 }
                }
                userDAOImp.deleteUser(user);
                writeResponse(new DeleteUserResponse(SUCCESS),response);
            }else
                writeResponse(new DeleteUserResponse(FAIL),response);
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void writeResponse(DeleteUserResponse deleteUserResponse, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(deleteUserResponse);
        response.getWriter().write(str);
    }

    public class DeleteUserResponse{
        int status;

        public DeleteUserResponse(int status) {
            this.status = status;
        }
    }
}

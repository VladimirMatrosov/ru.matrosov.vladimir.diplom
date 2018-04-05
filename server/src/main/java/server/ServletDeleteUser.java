package server;

import com.google.gson.Gson;
import data.*;
import generics.Response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static constant.RequestParameters.EMAIL_KEY;
import static constant.Status.FAIL;
import static constant.Status.SUCCESS;

@WebServlet(name = "ServletDeleteUser", urlPatterns = "/deleteUser")
public class ServletDeleteUser extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String email = request.getParameter(EMAIL_KEY);

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

                new Response<>(SUCCESS, null).writeResponse(response);
            }else
                new Response<>(FAIL, null).writeResponse(response);
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

package generics;

import com.google.gson.Gson;
import data.Message;
import data.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowMessageResponse {
    int status;
    List<User> users;
    List<Message> messages;

    public ShowMessageResponse(int status, List<User> users, List<Message> messages) {
        this.status = status;
        this.users = users;
        this.messages = messages;
    }

    public void writeResponse(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        String str = gson.toJson(this);
        response.getWriter().write(str);
    }
}

package generics;

import com.google.gson.Gson;
import server.ServletAddChat;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public class Response<T> {
    int status;
    T responseObject;

    public Response(int status, T responseObject) {
        this.status = status;
        this.responseObject = responseObject;
    }

    public void writeResponse(HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(this);
        response.getWriter().write(str);
    }
}

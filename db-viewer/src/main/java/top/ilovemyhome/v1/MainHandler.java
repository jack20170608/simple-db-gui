package top.ilovemyhome.v1;

import io.muserver.*;
import top.ilovemyhome.application.MuSession;

import java.util.Map;

public class MainHandler implements RouteHandler {

    @Override
    public void handle(MuRequest request, MuResponse response, Map<String, String> map) {
        MuSession session = (MuSession) request.attribute("mu.session");
        String user = (String) session.get("user");
        if (user != null) {
            response.status(200);
            response.contentType("application/json");
            response.write("{\"message\":\"Welcome to the main page, " + user + "!\"}");
        } else {
            response.status(401);
            response.contentType("application/json");
            response.write("{\"message\":\"Unauthorized\"}");
        }
    }
}
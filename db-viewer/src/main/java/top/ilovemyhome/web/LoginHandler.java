package top.ilovemyhome.web;

import io.muserver.*;
import org.json.JSONObject;
import top.ilovemyhome.application.AppContext;
import top.ilovemyhome.application.MuSession;
import top.ilovemyhome.common.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginHandler implements RouteHandler {
    private static final Map<String, String> users = new HashMap<>();

    static {
        // 模拟的用户数据库
        users.put("jack", "1");
    }

    public LoginHandler(AppContext appContext) {
    }

    @Override
    public void handle(MuRequest request, MuResponse response, Map<String, String> map) throws Exception {
        String body = request.readBodyAsString();
        JSONObject jsonObject = new JSONObject(body);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        if (users.containsKey(username) && users.get(username).equals(password)) {
            Cookie newCookie = createCookie();
            response.addCookie(newCookie);
            response.status(200);
            response.contentType("application/json");
            response.write(success().toString(1));
        } else if (username.equalsIgnoreCase("oo")){
            response.status(401);
            response.contentType("application/json");
            response.write(failure().toString(1));
        }else {
            response.status(403);
            response.contentType("application/json");
            response.write(failure().toString(1));
        }
    }

    private JSONObject success(){
        JSONObject res = new JSONObject();
        res.put("success", true);
        res.put("username", "jack");
        res.put("message", "Welcome!");
        return res;
    }

    private JSONObject failure(){
        JSONObject res = new JSONObject();
        res.put("success", false);
        res.put("message", "301 Username or password not correct!");
        return res;
    }


    private Cookie createCookie() {
        return CookieBuilder.newCookie()
            .withName(Constants.SESSION_ID)
            .withValue(UUID.randomUUID().toString())
            .withMaxAgeInSeconds(2 * 60 * 60)
            .withPath("*") // only send over /model/*
            .secure(false) // only send over HTTPS
            .httpOnly(true) // disable JavaScript access
            .withSameSite("Lax") // Strict, Lax, or None
            .build();


    }
}
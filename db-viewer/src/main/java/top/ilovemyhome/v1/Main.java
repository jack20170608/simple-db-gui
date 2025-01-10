package top.ilovemyhome.v1;

import io.muserver.*;
import io.muserver.handlers.ResourceHandlerBuilder;
import top.ilovemyhome.application.MuSession;
import top.ilovemyhome.web.LoginHandler;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // 创建并启动 MuServer
        MuServer server = MuServerBuilder.muServer()
                .withHttpPort(3001)
                .withIdleTimeout(30, TimeUnit.MINUTES)
                .withMaxRequestSize(100_000_000)
                .withHttp2Config(Http2ConfigBuilder.http2EnabledIfAvailable())
                .addHandler(ResourceHandlerBuilder.classpathHandler("/static/public"))
                .addHandler(CreateContextHandler())
                .addHandler(Method.POST, "/api/login", new LoginHandler(null))
                .addHandler(Method.GET, "/api/main", new MainHandler())
                .start();

        System.out.println("Server started at " + server.uri());
    }

    private static MuHandler CreateContextHandler() {
        return (request, response) -> {
            // 初始化会话
            if (request.attribute("jsessionid") == null) {
                request.attribute("jsessionid", new MuSession());
            }
            return false;
        };
    }
}
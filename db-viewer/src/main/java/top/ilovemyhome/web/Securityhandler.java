package top.ilovemyhome.web;

import io.muserver.MuHandler;
import io.muserver.MuRequest;
import io.muserver.MuResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ilovemyhome.application.AppContext;

import java.util.Optional;

import static top.ilovemyhome.common.Constants.SESSION_ID;

public class Securityhandler implements MuHandler {



    private final String contextPath;

    public Securityhandler(AppContext appContext) {
        this.contextPath = appContext.getContextPath();
    }

    @Override
    public boolean handle(MuRequest muRequest, MuResponse muResponse) throws Exception {
        //1.0 Get the Session id from the request
        //2.0 Based on the session id to extract the session info
        // 2.1 if session not exists, or session expired, redirect to the login.html
        // 2.2 if session exists, return false
        Optional<String> sessionId = muRequest.cookie(SESSION_ID);
        if (sessionId.isPresent()) {
            log.info(sessionId.get());
            return false;
        } else {
            muResponse.redirect(String.format("/%s/login.html", contextPath));
            return true;
        }
    }

    private static final Logger log = LoggerFactory.getLogger(Securityhandler.class);
}

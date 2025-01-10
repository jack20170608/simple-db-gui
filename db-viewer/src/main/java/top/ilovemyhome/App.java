package top.ilovemyhome;

import com.typesafe.config.Config;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ilovemyhome.application.AppContext;
import top.ilovemyhome.application.WebServerBootstrap;
import top.ilovemyhome.peanotes.backend.common.config.ConfigLoader;

public class App {

    public static void main(String[] args) {
        LOGGER.info("Starting application.");
        String env = System.getProperty("env");
        if (StringUtils.isEmpty(env)){
            env = "local";
        }
        App app = new App();
        app.initAppContext(env);
        app.initWebServer(app.getAppContext());
    }

    public AppContext getAppContext() {
        return appContext;
    }

    public static App getInstance() {
        return APP;
    }

    private App() {
    }

    private void initAppContext(String env){
        String rootConfig = "config/application.conf";
        String envConfig = "config/application-" + env + ".conf";
        Config config = ConfigLoader.loadConfig(rootConfig, envConfig);
        this.appContext = new AppContext(config);
        this.appContext.init();
    }

    private void initWebServer(AppContext appContext){
        WebServerBootstrap.start(appContext);
    }

    private AppContext appContext;
    private static App APP;
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

}

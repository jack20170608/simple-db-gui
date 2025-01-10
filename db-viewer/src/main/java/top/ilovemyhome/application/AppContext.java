package top.ilovemyhome.application;

import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.ilovemyhome.common.db.SimpleDataSourceFactory;
import top.ilovemyhome.service.QueryService;
import top.ilovemyhome.service.QueryServiceImpl;

import java.util.HashMap;
import java.util.Map;


public class AppContext {

    public AppContext(Config config) {
        this.config = config;
    }

    private SimpleDataSourceFactory dataSourceFactory = null;


    public synchronized void init() {
        LOGGER.info("Init the application context..........");

        boolean dbReady = config.getBoolean("database.ready");
        if (dbReady) {
            dataSourceFactory = SimpleDataSourceFactory.getInstance(this.config);
        }
        //1.dao
        initDao();

        //2. service
        initService();

    }

    private void initDao() {

    }

    private void initService() {
        QueryService queryService = new QueryServiceImpl(this);
        BEAN_FACTORY.put(QueryService.class, queryService);
        BEAN_NAME_FACTORY.put("queryService", queryService);
    }

    public String getApplicationName() {
        return config.getString("name");
    }

    public String getContextPath(){
        return config.getString("server.context-path");
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName, Class<T> beanClass) {
        return (T) BEAN_FACTORY.getOrDefault(beanClass, (T) BEAN_NAME_FACTORY.get(beanName));
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AppContext.class);

    private final Config config;

    private final Map<Class<?>, Object> BEAN_FACTORY = new HashMap<>();
    private final Map<String, Object> BEAN_NAME_FACTORY = new HashMap<>();

    public Config getConfig() {
        return config;
    }

    public SimpleDataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

}

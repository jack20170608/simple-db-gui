package top.ilovemyhome.application;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MuSession {

    //The session id stored in the cache
    // Create a cache with expiration policies
    private static Cache<String, String> sessionMap = CacheBuilder.newBuilder()
        .expireAfterWrite(2, TimeUnit.HOURS)   // Expire entries 2 hours after they are written
        .expireAfterAccess(1, TimeUnit.HOURS) // Expire entries 1 hour after they are last accessed
        .initialCapacity(100)
        .maximumSize(100_000L)
        .build();


    public void set(String key, String value) {
        sessionMap.put(key, value);
    }

    public String get(String key) {
        return sessionMap.getIfPresent(key);
    }
}
package com.arcbees.concurrentrichtext.server.cache;

import com.google.inject.Inject;
import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

import java.util.Collections;

public final class DiffSyncCache {

    private static final String CACHE_NAME = "DiffSyncCache";

    public static String getCollabChannelKey(String channelKey) {
        return CACHE_NAME.concat("CollabChannel").concat(channelKey);
    }

    public static String getDocShadowKey(String clientKey) {
        return CACHE_NAME.concat("DocShadow").concat(clientKey);
    }

    public static String getBackupDocShadowKey(String clientKey) {
        return CACHE_NAME.concat("BackupDocShadow").concat(clientKey);
    }

    public static Object getDiffSyncHandlerKey(String channelKey) {
        return CACHE_NAME.concat("DiffSyncHandlerChannel").concat(channelKey);
    }

    public static Object getDiffSyncKey(String clientKey) {
        return CACHE_NAME.concat("DiffSyncClient").concat(clientKey);
    }

    private Cache cache;

    @Inject
    public DiffSyncCache() {
        try {
            CacheManager cacheManager = CacheManager.getInstance();
            cache = cacheManager.getCache(CACHE_NAME);
            if (cache == null) {
                CacheFactory cacheFactory = cacheManager.getCacheFactory();
                cache = cacheFactory.createCache(Collections.emptyMap());
                cacheManager.registerCache(CACHE_NAME, cache);
            }
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void put(Object key, Object value) {
        cache.put(key, value);
    }

    public Object get(Object key) {
        return cache.get(key);
    }

    public boolean containsKey(Object key) {
        return cache.containsKey(key);
    }

}

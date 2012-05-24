package com.arcbees.concurrentrichtext.server.cache;

import com.google.inject.Inject;

public class ServerCacheFactoryImpl implements ServerCacheFactory {

    private final DiffSyncCache cache;

    @Inject
    public ServerCacheFactoryImpl(DiffSyncCache cache) {
        this.cache = cache;
    }

    @Override
    public <U extends CacheItem> ServerCache<U> create(Object cacheKey) {
        return new ServerCache<U>(cache, cacheKey);
    }

}

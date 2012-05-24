package com.arcbees.concurrentrichtext.server.cache;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class ServerCache<T extends CacheItem> {

    private final Object cacheKey;
    private final DiffSyncCache cache;

    @Inject
    public ServerCache(DiffSyncCache cache, @Assisted Object cacheKey) {
        this.cacheKey = cacheKey;
        this.cache = cache;
    }

    @SuppressWarnings("unchecked")
    public T getObject() {
        return (T) cache.get(cacheKey);
    }

    public void putObject(T object) {
        cache.put(cacheKey, object);
    }

}

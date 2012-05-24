package com.arcbees.concurrentrichtext.server.cache;

public interface ServerCacheFactory {
    <U extends CacheItem> ServerCache<U> create(Object cacheKey);
}

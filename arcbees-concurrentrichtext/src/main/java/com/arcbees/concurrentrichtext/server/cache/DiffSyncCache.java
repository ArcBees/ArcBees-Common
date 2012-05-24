/*
 * Copyright 2011 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.concurrentrichtext.server.cache;

import java.util.Collections;
import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

public final class DiffSyncCache {
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

    private static final String CACHE_NAME = "DiffSyncCache";

    private Cache cache;

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

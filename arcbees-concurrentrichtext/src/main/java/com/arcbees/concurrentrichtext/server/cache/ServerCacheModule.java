package com.arcbees.concurrentrichtext.server.cache;

import com.google.inject.Singleton;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class ServerCacheModule extends HandlerModule {

    @Override
    protected void configureHandlers() {
        bind(ServerCacheFactory.class).to(ServerCacheFactoryImpl.class);
        bind(DiffSyncCache.class).in(Singleton.class);
    }

}

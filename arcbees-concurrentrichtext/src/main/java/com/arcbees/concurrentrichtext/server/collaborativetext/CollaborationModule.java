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

package com.arcbees.concurrentrichtext.server.collaborativetext;

import com.arcbees.concurrentrichtext.server.cache.DiffSyncCache;
import com.arcbees.concurrentrichtext.server.cache.ServerCacheFactory;
import com.arcbees.concurrentrichtext.server.cache.ServerCacheFactoryImpl;
import com.arcbees.concurrentrichtext.shared.collaborativetext.JoinChannel;
import com.arcbees.concurrentrichtext.shared.collaborativetext.LeaveChannel;
import com.arcbees.concurrentrichtext.shared.collaborativetext.SendEdits;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class CollaborationModule extends HandlerModule {

    @Override
    protected void configureHandlers() {
        install(new FactoryModuleBuilder().build(CollaborationChannelFactory.class));

        bind(CollaborationHandler.class).in(Singleton.class);
        bindHandler(JoinChannel.class, JoinChannelHandler.class);
        bindHandler(LeaveChannel.class, LeaveChannelHandler.class);
        bindHandler(SendEdits.class, SendEditsHandler.class);

        bind(ServerCacheFactory.class).to(ServerCacheFactoryImpl.class);
        bind(DiffSyncCache.class).in(Singleton.class);

    }
}

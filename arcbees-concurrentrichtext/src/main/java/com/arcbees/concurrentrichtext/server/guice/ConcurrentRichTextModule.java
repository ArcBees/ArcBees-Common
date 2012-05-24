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

package com.arcbees.concurrentrichtext.server.guice;

import com.gwtplatform.dispatch.server.guice.HandlerModule;

import com.arcbees.concurrentrichtext.server.LoggingHandler;
import com.arcbees.concurrentrichtext.server.cache.ServerCacheModule;
import com.arcbees.concurrentrichtext.server.concurrent.ConcurrentModule;
import com.arcbees.concurrentrichtext.server.diffsync.DiffSyncModule;
import com.arcbees.concurrentrichtext.shared.LoggingMessage;

public class ConcurrentRichTextModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        install(new ConcurrentModule());
        install(new ServerCacheModule());
        install(new DiffSyncModule());

        bindHandler(LoggingMessage.class, LoggingHandler.class);
    }
}

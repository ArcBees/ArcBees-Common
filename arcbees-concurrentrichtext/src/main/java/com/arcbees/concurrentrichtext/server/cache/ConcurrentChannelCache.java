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

import com.arcbees.concurrentrichtext.server.concurrent.ConcurrentClient;
import java.util.List;

public class ConcurrentChannelCache implements CacheItem {
    private static final long serialVersionUID = 1437632534111827692L;
    private final int clientCount;
    private final int uniqueCount;
    private final List<ConcurrentClient> clients;

    public ConcurrentChannelCache(int clientCount, int uniqueCount, List<ConcurrentClient> clients) {
        this.clientCount = clientCount;
        this.uniqueCount = uniqueCount;
        this.clients = clients;
    }

    public int getUniqueCount() {
        return uniqueCount;
    }

    public int getClientCount() {
        return clientCount;
    }

    public List<ConcurrentClient> getClients() {
        return clients;
    }
}

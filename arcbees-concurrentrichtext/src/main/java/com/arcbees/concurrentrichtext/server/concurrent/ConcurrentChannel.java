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

package com.arcbees.concurrentrichtext.server.concurrent;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import com.arcbees.concurrentrichtext.server.cache.ConcurrentChannelCache;
import com.arcbees.concurrentrichtext.server.cache.DiffSyncCache;
import com.arcbees.concurrentrichtext.server.cache.ServerCache;
import com.arcbees.concurrentrichtext.server.cache.ServerCacheFactory;
import com.arcbees.concurrentrichtext.server.diffsync.DifferentialSyncHandler;
import com.arcbees.concurrentrichtext.server.diffsync.PatchApplyException;
import com.arcbees.concurrentrichtext.shared.diffsync.Edits;
import java.util.ArrayList;
import java.util.List;

public class ConcurrentChannel implements ConcurrentConnectionListener {
    private final DifferentialSyncHandler diffSyncHandler;
    private final String channelKey;
    private final ServerCache<ConcurrentChannelCache> cache;
    private int clientCount;
    private int uniqueCount;
    private List<ConcurrentClient> clients;

    @Inject
    public ConcurrentChannel(final DifferentialSyncHandler diffSyncHandler, final ServerCacheFactory serverCacheFactory,
            @Assisted final String channelKey) {
        this.channelKey = channelKey;
        this.diffSyncHandler = diffSyncHandler;

        cache = serverCacheFactory.create(DiffSyncCache.getCollabChannelKey(channelKey));
        ConcurrentChannelCache concurrentChannelCache = getOrCreateCacheObject();

        clientCount = concurrentChannelCache.getClientCount();
        uniqueCount = concurrentChannelCache.getUniqueCount();
        clients = concurrentChannelCache.getClients();

        diffSyncHandler.initialize(channelKey);
    }

    public String getChannelKey() {
        return channelKey;
    }

    public int getClientCount() {
        return clientCount;
    }

    public String getNextChannelID() {
        loadCache();
        if (clientCount == 0) {
            uniqueCount = 0; // Reset
        }
        String channelID = channelKey + uniqueCount;
        uniqueCount++;
        saveCache();

        return channelID;
    }

    @Override
    public void onClientConnected(final ConcurrentClient client) {
        loadCache();
        diffSyncHandler.addClient(client.getClientChannel());
        clients.add(client);
        clientCount++;
        saveCache();
    }

    @Override
    public void onClientDisconnected(final ConcurrentClient client) {
        loadCache();
        diffSyncHandler.removeClient(client.getClientChannel());
        clients.remove(client);
        clientCount--;
        saveCache();
    }

    public void applyEditsToClient(List<Edits> editsList, String client) throws PatchApplyException {
        diffSyncHandler.applyEdits(editsList, client);
    }

    public List<Edits> getServerEdits(String client) {
        List<Edits> editsList = new ArrayList<Edits>();
        Edits edits = diffSyncHandler.getServerEditsForClient(client);
        editsList.add(edits);

        return editsList;
    }

    public ConcurrentClient getClient(String clientChannel) {
        for (ConcurrentClient client : clients) {
            if (clientChannel.equals(client.getClientChannel())) {
                return client;
            }
        }

        return null;
    }

    public String getText() {
        return diffSyncHandler.getText();
    }

    private ConcurrentChannelCache getOrCreateCacheObject() {
        ConcurrentChannelCache concurrentChannelCache = cache.getObject();
        if (concurrentChannelCache == null) {
            concurrentChannelCache = new ConcurrentChannelCache(0, 0, new ArrayList<ConcurrentClient>());
            cache.putObject(concurrentChannelCache);
        }

        return concurrentChannelCache;
    }

    private void saveCache() {
        cache.putObject(new ConcurrentChannelCache(clientCount, uniqueCount, clients));
    }

    private void loadCache() {
        ConcurrentChannelCache concurrentChannelCache = cache.getObject();
        clientCount = concurrentChannelCache.getClientCount();
        uniqueCount = concurrentChannelCache.getUniqueCount();
        clients = concurrentChannelCache.getClients();
    }
}

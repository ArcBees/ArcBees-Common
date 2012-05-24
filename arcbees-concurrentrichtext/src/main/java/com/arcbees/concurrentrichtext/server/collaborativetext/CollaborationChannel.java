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

import com.arcbees.concurrentrichtext.server.cache.CollaborationChannelCache;
import com.arcbees.concurrentrichtext.server.cache.DiffSyncCache;
import com.arcbees.concurrentrichtext.server.cache.ServerCache;
import com.arcbees.concurrentrichtext.server.cache.ServerCacheFactory;
import com.arcbees.concurrentrichtext.server.diffsync.DifferentialSyncHandler;
import com.arcbees.concurrentrichtext.server.diffsync.PatchApplyException;
import com.arcbees.concurrentrichtext.shared.diffsync.Edits;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import java.util.ArrayList;
import java.util.List;

public class CollaborationChannel implements CollaborationConnectionListener {

    private int clientCount;
    private int uniqueCount;
    private final String channelKey;
    private final DifferentialSyncHandler diffSyncHandler;
    private ServerCache<CollaborationChannelCache> cache;
    private List<CollaborationClient> clients;

    @Inject
    public CollaborationChannel(final DifferentialSyncHandler diffSyncHandler,
                                ServerCacheFactory serverCacheFactory, @Assisted String channelKey) {
        this.channelKey = channelKey;

        cache = serverCacheFactory.create(DiffSyncCache.getCollabChannelKey(channelKey));
        CollaborationChannelCache collabCache = getOrCreateCacheObject();
        clientCount = collabCache.getClientCount();
        uniqueCount = collabCache.getUniqueCount();
        clients = collabCache.getClients();

        this.diffSyncHandler = diffSyncHandler;
        diffSyncHandler.initialize(channelKey);
    }

    private CollaborationChannelCache getOrCreateCacheObject() {
        CollaborationChannelCache collabCache = cache.getObject();
        if (collabCache == null) {
            collabCache = new CollaborationChannelCache(0, 0,
                                                        new ArrayList<CollaborationClient>());
            cache.putObject(collabCache);
        }

        return collabCache;
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
    public void onClientConnected(CollaborationClient client) {
        loadCache();
        diffSyncHandler.addClient(client.getClientChannel());
        clients.add(client);
        clientCount++;
        saveCache();
    }

    private void saveCache() {
        cache.putObject(new CollaborationChannelCache(clientCount, uniqueCount,
                                                      clients));
    }

    private void loadCache() {
        CollaborationChannelCache collabCache = cache.getObject();
        clientCount = collabCache.getClientCount();
        uniqueCount = collabCache.getUniqueCount();
        clients = collabCache.getClients();
    }

    @Override
    public void onClientDisconnected(CollaborationClient client) {
        loadCache();
        diffSyncHandler.removeClient(client.getClientChannel());
        clients.remove(client);
        clientCount--;
        saveCache();
    }

    public void applyEditsToClient(List<Edits> editsList, String client)
            throws PatchApplyException {
        diffSyncHandler.applyEdits(editsList, client);
    }

    public List<Edits> getServerEdits(String client) {
        List<Edits> editsList = new ArrayList<Edits>();
        Edits edits = diffSyncHandler.getServerEditsForClient(client);
        editsList.add(edits);

        return editsList;
    }

    public CollaborationClient getClient(String clientChannel) {
        for (CollaborationClient client : clients) {
            if (clientChannel.equals(client.getClientChannel())) {
                return client;
            }
        }

        return null;
    }

    public String getText() {
        return diffSyncHandler.getText();
    }

}

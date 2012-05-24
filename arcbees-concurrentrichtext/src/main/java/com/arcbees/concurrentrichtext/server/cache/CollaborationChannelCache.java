package com.arcbees.concurrentrichtext.server.cache;

import com.arcbees.concurrentrichtext.server.collaborativetext.CollaborationClient;

import java.util.List;

public class CollaborationChannelCache implements CacheItem {

    private static final long serialVersionUID = 1437632534111827692L;
    private final int clientCount;
    private final int uniqueCount;
    private final List<CollaborationClient> clients;

    public CollaborationChannelCache(int clientCount, int uniqueCount,
                                     List<CollaborationClient> clients) {
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

    public List<CollaborationClient> getClients() {
        return clients;
    }

}

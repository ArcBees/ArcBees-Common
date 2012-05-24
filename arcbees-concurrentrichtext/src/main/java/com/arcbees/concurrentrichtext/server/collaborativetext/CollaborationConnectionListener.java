package com.arcbees.concurrentrichtext.server.collaborativetext;

public interface CollaborationConnectionListener {
    void onClientConnected(CollaborationClient client);

    void onClientDisconnected(CollaborationClient client);
}

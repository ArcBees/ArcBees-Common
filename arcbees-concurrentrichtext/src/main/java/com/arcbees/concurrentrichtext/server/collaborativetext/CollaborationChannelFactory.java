package com.arcbees.concurrentrichtext.server.collaborativetext;


public interface CollaborationChannelFactory {
    CollaborationChannel create(String channelKey);
}

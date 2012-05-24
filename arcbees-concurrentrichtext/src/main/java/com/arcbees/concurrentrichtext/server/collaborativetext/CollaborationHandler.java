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

import com.google.inject.Inject;

import com.arcbees.concurrentrichtext.server.cache.DiffSyncCache;
import com.arcbees.concurrentrichtext.server.diffsync.PatchApplyException;
import com.arcbees.concurrentrichtext.shared.collaborativetext.JoinChannelResult;
import com.arcbees.concurrentrichtext.shared.collaborativetext.SendEdits;
import com.arcbees.concurrentrichtext.shared.collaborativetext.SendEditsResult;
import com.arcbees.concurrentrichtext.shared.collaborativetext.SendEditsResultEdits;
import com.arcbees.concurrentrichtext.shared.collaborativetext.SendEditsResultRestore;
import com.arcbees.concurrentrichtext.shared.diffsync.Edits;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollaborationHandler {

    private Map<String, CollaborationChannel> collabChannels;
    private final CollaborationChannelFactory collabChannelFactory;

    @Inject
    public CollaborationHandler(final CollaborationChannelFactory collabChannelFactory, DiffSyncCache cache) {
        this.collabChannelFactory = collabChannelFactory;
        collabChannels = new HashMap<String, CollaborationChannel>();
    }

    public JoinChannelResult joinChannel(String channelKey) {
        String clientChannelKey = getClientChannelKey(channelKey);

        addClientToChannel(channelKey, clientChannelKey);

        String serverText = getServerTextForChannel(channelKey);
        JoinChannelResult result = new JoinChannelResult(clientChannelKey, serverText);

        return result;
    }

    private String getServerTextForChannel(String channelKey) {
        CollaborationChannel serverChannel = getServerChannel(channelKey);

        return serverChannel.getText();
    }

    private CollaborationChannel getServerChannel(String channelKey) {
        CollaborationChannel channel = collabChannels.get(channelKey);
        if (channel == null) {
            channel = createChannel(channelKey);
        }
        return channel;
    }

    public SendEditsResult receiveEdits(final SendEdits message) {
        String channelKey = message.getChannelKey();
        String clientChannelKey = message.getClientChannel();
        List<Edits> edits = message.getEdits();

        SendEditsResult msg = synchronizeEdits(channelKey, clientChannelKey, edits);

        return msg;
    }

    private SendEditsResult synchronizeEdits(String channelKey, String clientChannelKey, List<Edits> edits) {
        CollaborationChannel channel = getServerChannel(channelKey);
        if (channel == null) {
            createChannel(channelKey);
            addClientToChannel(channelKey, clientChannelKey);
        }
        SendEditsResult result;

        try {
            channel.applyEditsToClient(edits, clientChannelKey);
            edits = getEditsFromServer(channel, clientChannelKey);
            result = new SendEditsResultEdits(edits);
        } catch (PatchApplyException e) {
            result = new SendEditsResultRestore(e.getRestoreText(), e.getTargetVersion(), e.getVersion());
        }

        return result;
    }

    private List<Edits> getEditsFromServer(CollaborationChannel channel, String clientChannelKey) {
        List<Edits> edits;
        edits = channel.getServerEdits(clientChannelKey);
        return edits;
    }

    public void leaveChannel(String clientChannel, String channelKey) {
        CollaborationChannel channel = getServerChannel(channelKey);
        CollaborationClient client = channel.getClient(clientChannel);

        if (client != null) {
            channel.onClientDisconnected(client);
        }
    }

    private String getClientChannelKey(String channelKey) {
        CollaborationChannel channel = getServerChannel(channelKey);
        if (channel == null) {
            channel = createChannel(channelKey);
        }

        return channel.getNextChannelID();
    }

    private CollaborationChannel createChannel(String channelKey) {
        CollaborationChannel channel = collabChannelFactory.create(channelKey);
        collabChannels.put(channelKey, channel);
        return channel;
    }

    private CollaborationClient addClientToChannel(String channelKey, String clientChannelKey) {
        CollaborationChannel channel = getServerChannel(channelKey);

        CollaborationClient client = new CollaborationClient(channelKey, clientChannelKey);
        channel.onClientConnected(client);

        return client;
    }
}

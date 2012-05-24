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

public class ConcurrentHandler {
    private final Map<String, ConcurrentChannel> concurrentChannels;
    private final ConcurrentChannelFactory concurrentChannelFactory;

    @Inject
    public ConcurrentHandler(final ConcurrentChannelFactory concurrentChannelFactory) {
        this.concurrentChannelFactory = concurrentChannelFactory;
        concurrentChannels = new HashMap<String, ConcurrentChannel>();
    }

    public JoinChannelResult joinChannel(String channelKey) {
        String clientChannelKey = getClientChannelKey(channelKey);

        addClientToChannel(channelKey, clientChannelKey);

        String serverText = getServerTextForChannel(channelKey);

        return new JoinChannelResult(clientChannelKey, serverText);
    }

    public SendEditsResult receiveEdits(final SendEdits message) {
        String channelKey = message.getChannelKey();
        String clientChannelKey = message.getClientChannel();
        List<Edits> edits = message.getEdits();

        return synchronizeEdits(channelKey, clientChannelKey, edits);
    }

    public void leaveChannel(String clientChannel, String channelKey) {
        ConcurrentChannel channel = getServerChannel(channelKey);
        ConcurrentClient client = channel.getClient(clientChannel);

        if (client != null) {
            channel.onClientDisconnected(client);
        }
    }

    private String getServerTextForChannel(String channelKey) {
        ConcurrentChannel serverChannel = getServerChannel(channelKey);

        return serverChannel.getText();
    }

    private ConcurrentChannel getServerChannel(String channelKey) {
        ConcurrentChannel channel = concurrentChannels.get(channelKey);

        if (channel == null) {
            channel = createChannel(channelKey);
        }

        return channel;
    }

    private SendEditsResult synchronizeEdits(String channelKey, String clientChannelKey, List<Edits> edits) {
        ConcurrentChannel channel = getServerChannel(channelKey);
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

    private List<Edits> getEditsFromServer(ConcurrentChannel channel, String clientChannelKey) {
        return channel.getServerEdits(clientChannelKey);
    }

    private String getClientChannelKey(String channelKey) {
        ConcurrentChannel channel = getServerChannel(channelKey);
        if (channel == null) {
            channel = createChannel(channelKey);
        }

        return channel.getNextChannelID();
    }

    private ConcurrentChannel createChannel(String channelKey) {
        ConcurrentChannel channel = concurrentChannelFactory.create(channelKey);
        concurrentChannels.put(channelKey, channel);

        return channel;
    }

    private ConcurrentClient addClientToChannel(String channelKey, String clientChannelKey) {
        ConcurrentChannel channel = getServerChannel(channelKey);

        ConcurrentClient client = new ConcurrentClient(channelKey, clientChannelKey);
        channel.onClientConnected(client);

        return client;
    }
}

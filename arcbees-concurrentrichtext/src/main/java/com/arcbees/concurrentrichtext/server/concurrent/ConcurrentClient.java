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

import java.io.Serializable;

public class ConcurrentClient implements Serializable {
    private static final long serialVersionUID = 3748845967720209613L;
    private final String channel;
    private final String clientChannel;

    public ConcurrentClient(String channel, String clientChannel) {
        this.channel = channel;
        this.clientChannel = clientChannel;
    }

    public String getChannel() {
        return channel;
    }

    public String getClientChannel() {
        return clientChannel;
    }
}

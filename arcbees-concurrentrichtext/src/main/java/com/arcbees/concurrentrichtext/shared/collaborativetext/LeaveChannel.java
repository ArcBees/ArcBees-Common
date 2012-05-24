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

package com.arcbees.concurrentrichtext.shared.collaborativetext;

import com.arcbees.concurrentrichtext.shared.NoResult;
import com.gwtplatform.dispatch.shared.ActionImpl;

public class LeaveChannel extends ActionImpl<NoResult> {

    private String clientChannel;
    private String channelKey;

    @SuppressWarnings("unused")
    private LeaveChannel() {

    }

    public LeaveChannel(String clientChannel, String channelKey) {
        this.clientChannel = clientChannel;
        this.channelKey = channelKey;
    }

    public String getClientChannel() {
        return clientChannel;
    }

    public String getChannelKey() {
        return channelKey;
    }

    @Override
    public boolean isSecured() {
        return false;
    }

}

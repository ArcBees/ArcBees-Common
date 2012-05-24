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

import com.arcbees.concurrentrichtext.shared.collaborativetext.JoinChannel;
import com.arcbees.concurrentrichtext.shared.collaborativetext.JoinChannelResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class JoinChannelHandler extends
                                AbstractActionHandler<JoinChannel, JoinChannelResult> {

    private final CollaborationHandler collaborationHandler;

    @Inject
    public JoinChannelHandler(CollaborationHandler collaborationHandler) {
        super(JoinChannel.class);

        this.collaborationHandler = collaborationHandler;
    }

    @Override
    public JoinChannelResult execute(JoinChannel action, ExecutionContext context)
            throws ActionException {
        JoinChannelResult result = collaborationHandler.joinChannel(action.getChannelKey());
        return result;
    }

    @Override
    public void undo(JoinChannel action, JoinChannelResult result,
                     ExecutionContext context) throws ActionException {

    }

}

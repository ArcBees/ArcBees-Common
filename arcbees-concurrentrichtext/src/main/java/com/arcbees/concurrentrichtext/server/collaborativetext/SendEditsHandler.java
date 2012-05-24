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

import com.arcbees.concurrentrichtext.shared.collaborativetext.SendEdits;
import com.arcbees.concurrentrichtext.shared.collaborativetext.SendEditsResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class SendEditsHandler extends
                              AbstractActionHandler<SendEdits, SendEditsResult> {

    private final CollaborationHandler collaborationHandler;

    @Inject
    public SendEditsHandler(CollaborationHandler collaborationHandler) {
        super(SendEdits.class);
        this.collaborationHandler = collaborationHandler;
    }

    @Override
    public SendEditsResult execute(SendEdits action, ExecutionContext context)
            throws ActionException {
        SendEditsResult result = collaborationHandler.receiveEdits(action);
        return result;
    }

    @Override
    public void undo(SendEdits action, SendEditsResult result,
                     ExecutionContext context) throws ActionException {

    }

}

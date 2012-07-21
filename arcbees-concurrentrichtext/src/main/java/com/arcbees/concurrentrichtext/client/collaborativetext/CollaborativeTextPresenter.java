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

package com.arcbees.concurrentrichtext.client.collaborativetext;

import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;
import com.google.web.bindery.event.shared.EventBus;

import com.arcbees.concurrentrichtext.client.diffsync.ApplyEditsResultOffset;
import com.arcbees.concurrentrichtext.client.diffsync.DifferentialSync;
import com.arcbees.concurrentrichtext.client.diffsync.DifferentialSyncCallback;
import com.arcbees.concurrentrichtext.client.diffsync.DifferentialSyncFactory;
import com.arcbees.concurrentrichtext.client.dispatch.AsyncCallbackImpl;
import com.arcbees.concurrentrichtext.client.util.ClientLogger;
import com.arcbees.concurrentrichtext.shared.AdaptativeTimer;
import com.arcbees.concurrentrichtext.shared.LoggingMessage.LogLevel;
import com.arcbees.concurrentrichtext.shared.NoResult;
import com.arcbees.concurrentrichtext.shared.collaborativetext.JoinChannel;
import com.arcbees.concurrentrichtext.shared.collaborativetext.JoinChannelFactory;
import com.arcbees.concurrentrichtext.shared.collaborativetext.JoinChannelResult;
import com.arcbees.concurrentrichtext.shared.collaborativetext.LeaveChannel;
import com.arcbees.concurrentrichtext.shared.collaborativetext.SendEdits;
import com.arcbees.concurrentrichtext.shared.collaborativetext.SendEditsResult;
import com.arcbees.concurrentrichtext.shared.collaborativetext.SendEditsResultEdits;
import com.arcbees.concurrentrichtext.shared.collaborativetext.SendEditsResultRestore;
import com.arcbees.concurrentrichtext.shared.diffsync.Edits;
import java.util.List;

public class CollaborativeTextPresenter extends PresenterWidget<CollaborativeTextPresenter.MyView>
        implements CollaborativeTextUiHandlers {
    public interface MyView extends View, HasUiHandlers<CollaborativeTextUiHandlers> {
        String getText();

        void setText(String text);

        String getTitle();

        void setTitle(String title);

        CursorOffset getCursorOffset();

        void setCursorOffset(CursorOffset cursor);

        void leaveEditMode();

        void setEditable(boolean editable);
    }

    private final DispatchAsync dispatcher;
    private final String channelKey;
    private final DifferentialSyncFactory diffSyncFactory;
    private final ClientLogger logger;
    private final JoinChannelFactory joinChannelFactory;
    private String clientChannel;
    private boolean editing;
    private AdaptativeTimer timerEdit;
    private DifferentialSync diffSync;

    @Inject
    public CollaborativeTextPresenter(final EventBus eventBus, final MyView view, final DispatchAsync dispatcher,
            final JoinChannelFactory joinChannelFactory, final DifferentialSyncFactory diffSyncFactory,
            final ClientLogger logger, @Named("DiffTimer") final int diffTimerDelay, @Assisted final String contentID) {
        super(eventBus, view);

        this.channelKey = contentID;
        this.dispatcher = dispatcher;
        this.joinChannelFactory = joinChannelFactory;
        this.diffSyncFactory = diffSyncFactory;
        this.logger = logger;

        initialize(diffTimerDelay);
    }

    @Override
    public void onJoinChannel() {
        if (!editing) {
            editing = true;
            joinChannel();
        }
    }

    @Override
    protected void onUnbind() {
        super.onUnbind();

        leaveChannel();
    }

    private void initialize(int diffTimerDelay) {
        getView().setUiHandlers(this);
        timerEdit = new AdaptativeTimer(diffTimerDelay, diffTimerDelay) {
            @Override
            public void timerTick() {
                diff();
            }
        };
        editing = false;
    }

    private void diff() {
        String text = getView().getText();
        diffSync.onTextChange(text);
    }

    private void joinChannel() {
        JoinChannel action = joinChannelFactory.create(channelKey);
        dispatcher.execute(action, new AsyncCallback<JoinChannelResult>() {
            @Override
            public void onSuccess(JoinChannelResult response) {
                onChannelJoined(response);
            }

            @Override
            public void onFailure(Throwable caught) {
                getView().leaveEditMode();
            }
        });
    }

    private void sendEdits(List<Edits> editsList) {
        SendEdits action = new SendEdits(editsList, clientChannel, channelKey);
        dispatcher.execute(action, new AsyncCallbackImpl<SendEditsResult>() {
            @Override
            public void onSuccess(SendEditsResult result) {
                onMessageReceived(result);
            }
        });
    }

    private void onChannelJoined(final JoinChannelResult result) {
        String baseText = result.getServerText();
        clientChannel = result.getClientChannel();

        diffSync = diffSyncFactory.create(new DifferentialSyncCallback() {
            @Override
            public void onSynchronize(List<Edits> editsList) {
                sendEdits(editsList);
            }
        });

        diffSync.restore(baseText, 0, 0);
        getView().setTitle("OK to edit text");
        getView().setText(baseText);
        getView().setEditable(true);
        timerEdit.start();
    }

    private void onMessageReceived(SendEditsResult message) {
        SendEditsResult.ResultType msgType = message.getResultType();
        boolean textChanged = false;
        ApplyEditsResultOffset applyResult = null;

//        getView().setEditable(false);
        CursorOffset cursor = getView().getCursorOffset();
        if (msgType.equals(SendEditsResult.ResultType.EDITS)) {
            SendEditsResultEdits editsMessage = (SendEditsResultEdits) message;
            applyResult = diffSync.onEditsReceived(editsMessage.getEdits(), cursor);
            textChanged = applyResult.hasAppliedEdits();
        } else if (msgType.equals(SendEditsResult.ResultType.RESTORE)) {
            SendEditsResultRestore restoreMessage = (SendEditsResultRestore) message;
            log(LogLevel.SEVERE, makeRestoreMessageString(restoreMessage));
            diffSync.restore(restoreMessage.getText(), restoreMessage.getClientVersion(),
                    restoreMessage.getServerVersion());
            textChanged = true;
        }

        if (textChanged) {
            getView().setText(diffSync.getText());
            if (applyResult != null) {
                cursor = applyResult.getCursorOffset();
            }
            getView().setCursorOffset(cursor);
        }
//        getView().setEditable(true);
    }

    private String makeRestoreMessageString(SendEditsResultRestore restoreMessage) {
        return new StringBuilder().append("Client ").append(clientChannel).append(" received RESTORE message : ")
                .append(restoreMessage.getClientVersion()).append("-").append(restoreMessage.getServerVersion())
                .append("-").append(restoreMessage.getText()).toString();
    }

    private void log(LogLevel level, String message) {
        logger.log(level, message);
    }

    private void leaveChannel() {
        LeaveChannel action = new LeaveChannel(clientChannel, channelKey);
        dispatcher.execute(action, new AsyncCallbackImpl<NoResult>() {
            @Override
            public void onSuccess(NoResult result) {
                getView().leaveEditMode();
            }
        });
    }
}

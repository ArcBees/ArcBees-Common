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

package com.arcbees.concurrentrichtext.client.diffsync;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import com.arcbees.concurrentrichtext.client.collaborativetext.CursorOffset;
import com.arcbees.concurrentrichtext.client.util.ClientLogger;
import com.arcbees.concurrentrichtext.shared.LoggingMessage.LogLevel;
import com.arcbees.concurrentrichtext.shared.diffsync.DiffHandler;
import com.arcbees.concurrentrichtext.shared.diffsync.DocumentShadow;
import com.arcbees.concurrentrichtext.shared.diffsync.Edits;
import com.arcbees.concurrentrichtext.shared.diffsync.PatchResultOffset;
import java.util.LinkedList;
import java.util.List;

public abstract class AbstractDifferentialSync implements DifferentialSync {
    protected final LinkedList<Edits> editsQueue;
    private final DocumentShadow docShadow;
    private final DiffHandler diffHandler;
    private final DifferentialSyncCallback callback;
    private final ClientLogger logger;
    private Boolean readyToSend;
    private String currentText;
    private Integer lastAcknowledge;

    @Inject
    public AbstractDifferentialSync(final DiffHandler diffHandler, final DocumentShadow docShadow,
            final ClientLogger logger, @Assisted final DifferentialSyncCallback callback) {
        this.diffHandler = diffHandler;
        this.docShadow = docShadow;
        this.logger = logger;
        this.callback = callback;
        editsQueue = new LinkedList<Edits>();
        currentText = "";
        readyToSend = true;
        lastAcknowledge = 0;
    }

    @Override
    public final void restore(String text, int clientVersion, int serverVersion) {
        editsQueue.clear();
        docShadow.restore(clientVersion, serverVersion, text);
        currentText = text;
        readyToSend = true;
    }

    @Override
    public final void onTextChange(String newText) {
        Edits edits = diffHandler.getEdits(docShadow, newText);

        if (edits.hasEdits()) {
            docShadow.updateText(newText);
        }

        currentText = newText;
        addEditsToQueue(edits);
    }

    @Override
    public String getText() {
        return currentText;
    }

    @Override
    public final ApplyEditsResultOffset onEditsReceived(List<Edits> editsList, CursorOffset cursor) {
        ApplyEditsResultOffset applyEditsResult = applyEdits(editsList, cursor);

        cleanupEditsQueue();

        return applyEditsResult;
    }

    protected abstract void editsAdded();

    protected final void synchronize() {
        if (readyToSend) {
            readyToSend = false;
            callback.onSynchronize(editsQueue);
        }
    }

    private ApplyEditsResultOffset applyEdits(List<Edits> editsList, CursorOffset cursor) {
        boolean hasAppliedEdits = false;
        for (Edits edits : editsList) {
            if (edits.hasEdits()) {
                docShadow.applyEdits(edits);
                PatchResultOffset result = diffHandler.applyEdits(edits, cursor, currentText);
                currentText = result.getNewText();
                cursor = result.getCursorOffset();
                hasAppliedEdits = true;
            }

            lastAcknowledge = edits.getTargetVersion();
        }

        logger.log(LogLevel.WARNING, "LastAcknowledge : " + lastAcknowledge);

        return new ApplyEditsResultOffset(hasAppliedEdits, cursor);
    }

    private void addEditsToQueue(Edits edits) {
        if (editsQueue.size() == 0 || edits.hasEdits()) {
            logger.log(LogLevel.WARNING,
                    "Client queuing edits : " + edits.getVersion() + "-" + edits.getTargetVersion());
            editsQueue.add(edits);
            editsAdded();
        }
    }

    private void cleanupEditsQueue() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < editsQueue.size(); i++) {
            Edits edits = editsQueue.get(i);
            if (lastAcknowledge > edits.getVersion()) {
                // TODO : Figure out why this is commented
                // if (docShadow.getTargetVersion() >= edits.getTargetVersion()) {
                sb.append("Removing edits from queue : " + edits.getVersion() + "-" + edits.getTargetVersion());
                editsQueue.remove(i);
                i--;
            }
        }

        logger.log(LogLevel.WARNING, sb.toString());
        readyToSend = true;
    }
}

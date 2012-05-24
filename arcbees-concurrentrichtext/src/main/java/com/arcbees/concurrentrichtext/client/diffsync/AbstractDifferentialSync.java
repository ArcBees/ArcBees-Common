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
    private boolean readyToSend;
    private String currentText;
    private final ClientLogger logger;

    private int lastAcknowledge;

    @Inject
    public AbstractDifferentialSync(DiffHandler diffHandler,
                                    DocumentShadow docShadow, ClientLogger logger,
                                    @Assisted DifferentialSyncCallback callback) {
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
        docShadow.restore(clientVersion, serverVersion, text);
        currentText = text;
        editsQueue.clear();
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

    private void addEditsToQueue(Edits edits) {
        if (editsQueue.size() == 0 || edits.hasEdits()) {
            logger.log(
                    LogLevel.WARNING,
                    "Client queuing edits : " + edits.getVersion() + "-"
                    + edits.getTargetVersion());
            editsQueue.add(edits);
            editsAdded();
        }
    }

    @Override
    public String getText() {
        return currentText;
    }

    @Override
    public final ApplyEditsResultOffset onEditsReceived(List<Edits> editsList,
                                                        CursorOffset cursor) {
        ApplyEditsResultOffset applyEditsResult = applyEdits(editsList, cursor);

        cleanupEditsQueue();

        return applyEditsResult;
    }

    private ApplyEditsResultOffset applyEdits(List<Edits> editsList,
                                              CursorOffset cursor) {
        boolean hasAppliedEdits = false;
        for (Edits edits : editsList) {
            if (edits.hasEdits()) {
                docShadow.applyEdits(edits);
                PatchResultOffset result = diffHandler.applyEdits(edits, cursor,
                                                                  currentText);
                currentText = result.getNewText();
                cursor = result.getCursorOffset();
                hasAppliedEdits = true;
            }
            lastAcknowledge = edits.getTargetVersion();
        }

        logger.log(LogLevel.WARNING, "LastAcknowledge : " + lastAcknowledge);

        return new ApplyEditsResultOffset(hasAppliedEdits, cursor);
    }

    private void cleanupEditsQueue() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < editsQueue.size(); i++) {
            Edits edits = editsQueue.get(i);
            if (lastAcknowledge > edits.getVersion()) {
                // if (docShadow.getTargetVersion() >= edits.getTargetVersion()) {
                sb.append("Removing edits from queue : " + edits.getVersion() + "-"
                          + edits.getTargetVersion());
                editsQueue.remove(i);
                i--;
            }
        }
        logger.log(LogLevel.WARNING, sb.toString());
        readyToSend = true;
    }

    protected final void synchronize() {
        if (readyToSend) {
            readyToSend = false;
            callback.onSynchronize(editsQueue);
        }
    }

    protected abstract void editsAdded();

}

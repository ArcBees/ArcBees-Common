package com.arcbees.concurrentrichtext.shared.diffsync;

import com.google.inject.Inject;

public abstract class AbstractDocumentShadow implements DocumentShadow {

    public enum DocumentType {
        Client, Server
    }

    ;

    private int clientVersion;
    private int serverVersion;
    private DiffHandler diffHandler;
    private String text;
    private String id;
    protected DocumentShadow backupShadow;

    @Inject
    public AbstractDocumentShadow(DiffHandler diffHandler) {
        this.diffHandler = diffHandler;
        clientVersion = 0;
        serverVersion = 0;
        text = "";
    }

    @Override
    public final synchronized void initialize(String id, String baseText) {
        text = baseText;
    }

    @Override
    public final synchronized void restoreFromCache(String id, int clientVersion,
                                                    int serverVersion, String text) {
        this.id = id;
        loadCache();
    }

    protected abstract void loadCache();

    protected abstract void saveCache();

    protected String getId() {
        return id;
    }

    @Override
    public final synchronized void restore(int clientVersion, int serverVersion,
                                           String text) {
        this.text = text;
        this.clientVersion = clientVersion;
        this.serverVersion = serverVersion;
    }

    @Override
    public final synchronized void restoreFromBackup() {
        if (backupShadow != null) {
            restore(backupShadow.getVersion(), backupShadow.getTargetVersion(),
                    backupShadow.getText());
        }
    }

    protected final Object clone() {
        DocumentShadow cloneDocShadow = createNewDocument();

        cloneDocShadow.restore(clientVersion, serverVersion, text);

        return cloneDocShadow;
    }

    protected abstract DocumentShadow createNewDocument();

    private void updateVersion() {
        if (getDocType().equals(DocumentType.Client)) {
            clientVersion++;
        } else {
            serverVersion++;
        }
    }

    @Override
    public synchronized final ApplyEditsResult applyEdits(Edits edits) {
        ApplyEditsResult applyResult;

        if (versionsMatches(edits)) {
            applyResult = doApplyEdits(edits);
        } else if (versionInPast(edits)) {
            String side = getDocType().equals(DocumentType.Client) ? "Client"
                                                                   : "Server";
            debugLog(side + " receiving past edits (", edits);
            applyResult = ApplyEditsResult.AlreadyReceived;
        } else {
            String side = getDocType().equals(DocumentType.Client) ? "Client"
                                                                   : "Server";
            debugLog(side + " version doesn't match (", edits);
            applyResult = ApplyEditsResult.VersionMismatch;
        }

        return applyResult;
    }

    private void debugLog(String message, Edits edits) {
        System.out.println(new StringBuilder().append(message).append("Doc : ").append(
                getVersion()).append("-").append(getTargetVersion()).append(
                " === Edits : ").append(edits.getTargetVersion()).append("-").append(
                edits.getVersion()).append(")").toString());
    }

    private ApplyEditsResult doApplyEdits(Edits edits) {
        ApplyEditsResult applyResult;
        PatchResult patchResult = diffHandler.applyEdits(edits, text);
        boolean[] patchSuccess = patchResult.getPatchesResults();
        boolean success = patchSucceed(patchSuccess);

        if (!success) {
            debugLog("Error applying patches - ", edits);
            applyResult = ApplyEditsResult.Failed;
        } else {
            text = patchResult.getNewText();
            updateVersionNumber();
            applyResult = ApplyEditsResult.Success;
        }

        return applyResult;
    }

    @Override
    public boolean versionInPast(Edits edits) {
        return (getVersion() >= edits.getTargetVersion() && getTargetVersion() >= edits.getVersion());
    }

    @Override
    public boolean versionsMatches(Edits edits) {
        return (getVersion() == edits.getTargetVersion() && getTargetVersion() == edits.getVersion());
    }

    public abstract int getTargetVersion();

    protected abstract DocumentType getDocType();

    private void updateVersionNumber() {
        if (getDocType().equals(DocumentType.Client)) {
            serverVersion++;
        } else {
            clientVersion++;
            backupShadow = (DocumentShadow) clone();
            saveCache();
        }
    }

    protected final int getClientVersion() {
        return clientVersion;
    }

    protected final int getServerVersion() {
        return serverVersion;
    }

    @Override
    public final synchronized void updateText(String newText) {
        text = newText;
        updateVersion();
    }

    @Override
    public final String getText() {
        return text;
    }

    private boolean patchSucceed(boolean[] patchSuccess) {
        for (boolean success : patchSuccess) {
            if (!success) {
                return false;
            }
        }

        return true;
    }

}

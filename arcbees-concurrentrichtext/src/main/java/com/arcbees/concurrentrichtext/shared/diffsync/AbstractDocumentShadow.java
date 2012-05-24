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

package com.arcbees.concurrentrichtext.shared.diffsync;

import com.google.inject.Inject;

public abstract class AbstractDocumentShadow implements DocumentShadow {
    public enum DocumentType {
        Client, Server;
    }

    protected DocumentShadow backupShadow;
    private final DiffHandler diffHandler;
    private int clientVersion;
    private int serverVersion;
    private String text;
    private String id;

    @Inject
    public AbstractDocumentShadow(final DiffHandler diffHandler) {
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
    public final synchronized void restoreFromCache(String id, int clientVersion, int serverVersion, String text) {
        this.id = id;
        loadCache();
    }

    @Override
    public final synchronized void restore(int clientVersion, int serverVersion, String text) {
        this.text = text;
        this.clientVersion = clientVersion;
        this.serverVersion = serverVersion;
    }

    @Override
    public final synchronized void restoreFromBackup() {
        if (backupShadow != null) {
            restore(backupShadow.getVersion(), backupShadow.getTargetVersion(), backupShadow.getText());
        }
    }

    @Override
    public synchronized final ApplyEditsResult applyEdits(Edits edits) {
        ApplyEditsResult applyResult;

        if (versionsMatches(edits)) {
            applyResult = doApplyEdits(edits);
        } else if (versionInPast(edits)) {
            String side = getDocType().equals(DocumentType.Client) ? "Client" : "Server";
            debugLog(side + " receiving past edits (", edits);
            applyResult = ApplyEditsResult.AlreadyReceived;
        } else {
            String side = getDocType().equals(DocumentType.Client) ? "Client" : "Server";
            debugLog(side + " version doesn't match (", edits);
            applyResult = ApplyEditsResult.VersionMismatch;
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

    @Override
    public final synchronized void updateText(String newText) {
        text = newText;
        updateVersion();
    }

    @Override
    public final String getText() {
        return text;
    }

    public abstract int getTargetVersion();

    protected abstract void loadCache();

    protected abstract void saveCache();

    protected abstract DocumentShadow createNewDocument();

    protected abstract DocumentType getDocType();

    protected String getId() {
        return id;
    }

    protected final Object clone() {
        DocumentShadow cloneDocShadow = createNewDocument();

        cloneDocShadow.restore(clientVersion, serverVersion, text);

        return cloneDocShadow;
    }

    protected final int getClientVersion() {
        return clientVersion;
    }

    protected final int getServerVersion() {
        return serverVersion;
    }

    private void updateVersion() {
        if (getDocType().equals(DocumentType.Client)) {
            clientVersion++;
        } else {
            serverVersion++;
        }
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

    private void updateVersionNumber() {
        if (getDocType().equals(DocumentType.Client)) {
            serverVersion++;
        } else {
            clientVersion++;
            backupShadow = (DocumentShadow) clone();
            saveCache();
        }
    }

    private boolean patchSucceed(boolean[] patchSuccess) {
        for (boolean success : patchSuccess) {
            if (!success) {
                return false;
            }
        }

        return true;
    }

    private void debugLog(String message, Edits edits) {
        System.out.println(new StringBuilder().append(message).append("Doc : ").append(getVersion()).append("-")
                .append(getTargetVersion()).append(" === Edits : ").append(edits.getTargetVersion()).append("-")
                .append(edits.getVersion()).append(")").toString());
    }
}

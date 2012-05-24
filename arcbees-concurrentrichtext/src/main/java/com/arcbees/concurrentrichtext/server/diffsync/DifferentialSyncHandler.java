package com.arcbees.concurrentrichtext.server.diffsync;

import com.arcbees.concurrentrichtext.server.cache.DiffSyncCache;
import com.arcbees.concurrentrichtext.server.cache.DifferentialSyncHandlerCache;
import com.arcbees.concurrentrichtext.server.cache.ServerCache;
import com.arcbees.concurrentrichtext.server.cache.ServerCacheFactory;
import com.arcbees.concurrentrichtext.shared.diffsync.ApplyEditsResult;
import com.arcbees.concurrentrichtext.shared.diffsync.DiffHandler;
import com.arcbees.concurrentrichtext.shared.diffsync.DocumentShadow;
import com.arcbees.concurrentrichtext.shared.diffsync.Edits;
import com.arcbees.concurrentrichtext.shared.diffsync.PatchResult;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DifferentialSyncHandler {

    private String serverText;
    private final Map<String, DifferentialSync> clients;
    private final Provider<DifferentialSync> diffSyncProvider;
    private ServerCache<DifferentialSyncHandlerCache> cache;
    private final DiffHandler diffHandler;
    private final ServerCacheFactory serverCacheFactory;
    private final Logger logger;

    @Inject
    public DifferentialSyncHandler(final Logger logger, DiffHandler diffHandler,
                                   Provider<DifferentialSync> diffSyncProvider,
                                   ServerCacheFactory serverCacheFactory) {
        this.logger = logger;
        this.diffHandler = diffHandler;
        this.diffSyncProvider = diffSyncProvider;
        this.serverCacheFactory = serverCacheFactory;
        serverText = "";
        clients = new HashMap<String, DifferentialSync>();
    }

    public void initialize(String channelKey) {
        cache = serverCacheFactory.create(DiffSyncCache.getDiffSyncHandlerKey(channelKey));
        loadCache();
    }

    private void loadCache() {
        DifferentialSyncHandlerCache diffCache = cache.getObject();
        if (diffCache != null) {
            serverText = diffCache.getText();
        } else {
            serverText = "";
            saveCache();
        }
    }

    private void saveCache() {
        cache.putObject(new DifferentialSyncHandlerCache(serverText));
    }

    public void addClient(String client) {
        loadCache();
        DifferentialSync diffSync = diffSyncProvider.get();
        diffSync.initialize(client, serverText);
        clients.put(client, diffSync);
    }

    public synchronized void applyEdits(List<Edits> editsList, String client)
            throws PatchApplyException {
        DifferentialSync diffSync = getClient(client);
        debugLogEdits(editsList);
        for (Edits edits : editsList) {
            if (edits.hasEdits()) {
                logger.log(Level.WARNING, "Received Edits : " + edits.getVersion()
                                          + "-" + edits.getTargetVersion() + "==="
                                          + diffSync.getDocumentShadow().getVersion() + "-"
                                          + diffSync.getDocumentShadow().getTargetVersion());
                logger.log(Level.WARNING, edits.getDiffs().toString());
                logger.log(Level.WARNING, diffSync.getDocumentShadow().getText());
                ApplyEditsResult applyResult = diffSync.applyEdits(edits);
                if (applyResult.equals(ApplyEditsResult.VersionMismatch)
                    || applyResult.equals(ApplyEditsResult.Failed)) {
                    logger.log(Level.SEVERE,
                               "PatchApplyException : " + applyResult.toString());
                    restoreAndThrow(diffSync, edits);
                } else if (applyResult.equals(ApplyEditsResult.Success)) {
                    patchServerText(edits);
                }
            }
        }
    }

    private void debugLogEdits(List<Edits> editsList) {
        StringBuilder b = new StringBuilder();
        for (Edits edits : editsList) {
            b.append(edits.getVersion()).append("-").append(edits.getTargetVersion());
            b.append('\n');
        }
        logger.log(Level.WARNING, b.toString());
    }

    private void patchServerText(Edits edits) {
        loadCache();
        PatchResult result = diffHandler.applyEdits(edits, serverText);
        serverText = result.getNewText();
        saveCache();
    }

    private void restoreAndThrow(DifferentialSync diffSync, Edits edits)
            throws PatchApplyException {
        DocumentShadow docShadow = diffSync.getDocumentShadow();
        logger.log(Level.SEVERE, "DocShadow : " + docShadow.getVersion() + "-"
                                 + docShadow.getTargetVersion() + "=== Edits : " + edits.getVersion()
                                 + "-" + edits.getTargetVersion());
        diffSync.restoreFromBackup();
        throw new PatchApplyException(docShadow.getText(),
                                      docShadow.getTargetVersion(), docShadow.getVersion());
    }

    public synchronized Edits getServerEditsForClient(String client) {
        loadCache();
        DifferentialSync diffSync = getClient(client);
        Edits clientEdits = diffSync.getEdits(serverText);

        return clientEdits;
    }

    public String getText() {
        loadCache();
        return serverText;
    }

    private DifferentialSync getClient(String client) {
        DifferentialSync diffSync = clients.get(client);
        if (diffSync == null) {
            addClient(client);
        }
        return clients.get(client);
    }

    public void removeClient(String clientChannel) {
        DifferentialSync diffSync = getClient(clientChannel);
        diffSync.leave();
        clients.remove(clientChannel);
    }

}

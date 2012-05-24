package com.arcbees.concurrentrichtext.server.diffsync;

import com.arcbees.concurrentrichtext.server.cache.DiffSyncCache;
import com.arcbees.concurrentrichtext.server.cache.DocumentShadowCache;
import com.arcbees.concurrentrichtext.server.cache.ServerCache;
import com.arcbees.concurrentrichtext.server.cache.ServerCacheFactory;
import com.arcbees.concurrentrichtext.shared.diffsync.ApplyEditsResult;
import com.arcbees.concurrentrichtext.shared.diffsync.DiffHandler;
import com.arcbees.concurrentrichtext.shared.diffsync.DocumentShadow;
import com.arcbees.concurrentrichtext.shared.diffsync.Edits;
import com.google.inject.Inject;

public class DifferentialSyncImpl implements DifferentialSync {

    private final DocumentShadow docShadow;
    private final DiffHandler diffHandler;
    private ServerCache<DocumentShadowCache> cache;
    private final ServerCacheFactory serverCacheFactory;
    private String id;

    @Inject
    public DifferentialSyncImpl(DocumentShadow docShadow,
                                DiffHandler diffHandler, ServerCacheFactory serverCacheFactory) {
        this.docShadow = docShadow;
        this.diffHandler = diffHandler;
        this.serverCacheFactory = serverCacheFactory;
    }

    @Override
    public ApplyEditsResult applyEdits(Edits edits) {
        loadCache();
        ApplyEditsResult result = docShadow.applyEdits(edits);
        saveCache();

        return result;
    }

    @Override
    public Edits getEdits(String serverText) {
        loadCache();
        Edits edits = diffHandler.getEdits(docShadow, serverText);

        if (edits.hasEdits()) {
            docShadow.updateText(serverText);
            saveCache();
        }

        return edits;
    }

    @Override
    public void initialize(String id, String baseText) {
        this.id = id;
        cache = serverCacheFactory.create(DiffSyncCache.getDiffSyncKey(id));
        DocumentShadowCache docCache = cache.getObject();
        if (docCache == null) {
            docShadow.initialize(id, baseText);
            saveCache();
        } else {
            loadCache();
        }
    }

    @Override
    public DocumentShadow getDocumentShadow() {
        loadCache();
        return docShadow;
    }

    private final void loadCache() {
        DocumentShadowCache docCache = cache.getObject();
        id = docCache.getId();
        docShadow.restoreFromCache(id, docCache.getClientVersion(),
                                   docCache.getServerVersion(), docCache.getText());
    }

    private final void saveCache() {
        cache.putObject(new DocumentShadowCache(id, docShadow.getText(),
                                                docShadow.getVersion(), docShadow.getTargetVersion()));
    }

    @Override
    public void restoreFromBackup() {
        loadCache();
        docShadow.restoreFromBackup();
        saveCache();
    }

    @Override
    public boolean checkVersion(Edits edits) {
        loadCache();
        boolean match = docShadow.versionsMatches(edits);
        if (!match) {
            match = !docShadow.versionInPast(edits);
        }
        return match;
    }

    @Override
    public void leave() {
        cache.putObject(null);
        saveCache();
    }
}

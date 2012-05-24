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

package com.arcbees.concurrentrichtext.server.diffsync;

import com.google.inject.Inject;

import com.arcbees.concurrentrichtext.server.cache.DiffSyncCache;
import com.arcbees.concurrentrichtext.server.cache.DocumentShadowCache;
import com.arcbees.concurrentrichtext.server.cache.ServerCache;
import com.arcbees.concurrentrichtext.server.cache.ServerCacheFactory;
import com.arcbees.concurrentrichtext.shared.diffsync.ApplyEditsResult;
import com.arcbees.concurrentrichtext.shared.diffsync.DiffHandler;
import com.arcbees.concurrentrichtext.shared.diffsync.DocumentShadow;
import com.arcbees.concurrentrichtext.shared.diffsync.Edits;

public class DifferentialSyncImpl implements DifferentialSync {
    private final DocumentShadow docShadow;
    private final DiffHandler diffHandler;
    private ServerCache<DocumentShadowCache> cache;
    private final ServerCacheFactory serverCacheFactory;
    private String id;

    @Inject
    public DifferentialSyncImpl(DocumentShadow docShadow, DiffHandler diffHandler,
            ServerCacheFactory serverCacheFactory) {
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

    @Override
    public void restoreFromBackup() {
        loadCache();
        docShadow.restoreFromBackup();
        saveCache();
    }

    @Override
    public boolean checkVersion(Edits edits) {
        loadCache();

        boolean versionsMatches = docShadow.versionsMatches(edits);
        if (!versionsMatches) {
            versionsMatches = !docShadow.versionInPast(edits);
        }

        return versionsMatches;
    }

    @Override
    public void leave() {
        cache.putObject(null);
        saveCache();
    }

    private void loadCache() {
        DocumentShadowCache docCache = cache.getObject();
        id = docCache.getId();
        docShadow.restoreFromCache(id, docCache.getClientVersion(), docCache.getServerVersion(), docCache.getText());
    }

    private void saveCache() {
        cache.putObject(
                new DocumentShadowCache(id, docShadow.getText(), docShadow.getVersion(), docShadow.getTargetVersion()));
    }
}

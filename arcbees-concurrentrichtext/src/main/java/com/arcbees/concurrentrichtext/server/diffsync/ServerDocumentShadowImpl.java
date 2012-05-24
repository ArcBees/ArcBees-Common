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
import com.arcbees.concurrentrichtext.shared.diffsync.AbstractDocumentShadow;
import com.arcbees.concurrentrichtext.shared.diffsync.DiffHandler;
import com.arcbees.concurrentrichtext.shared.diffsync.DocumentShadow;

public final class ServerDocumentShadowImpl extends AbstractDocumentShadow {
    private static final DocumentType docType = DocumentType.Server;
    private final ServerCacheFactory serverCacheFactory;
    private final DiffHandler diffHandler;
    private ServerCache<DocumentShadowCache> backupCache;

    @Inject
    public ServerDocumentShadowImpl(final DiffHandler diffHandler, final ServerCacheFactory serverCacheFactory) {
        super(diffHandler);

        this.diffHandler = diffHandler;
        this.serverCacheFactory = serverCacheFactory;
    }

    @Override
    public int getTargetVersion() {
        return getClientVersion();
    }

    @Override
    public int getVersion() {
        return getServerVersion();
    }

    @Override
    protected void loadCache() {
        if (backupCache == null) {
            backupCache = serverCacheFactory.create(DiffSyncCache.getBackupDocShadowKey(getId()));
        }

        DocumentShadowCache docCache = backupCache.getObject();
        if (docCache != null) {
            if (backupShadow == null) {
                backupShadow = createNewDocument();
            }
            backupShadow.restore(docCache.getClientVersion(), docCache.getServerVersion(), docCache.getText());
        }
    }

    @Override
    protected void saveCache() {
        backupCache.putObject(new DocumentShadowCache(getId(), backupShadow.getText(), backupShadow.getVersion(),
                backupShadow.getTargetVersion()));
    }

    @Override
    protected DocumentShadow createNewDocument() {
        return new ServerDocumentShadowImpl(diffHandler, serverCacheFactory);
    }

    @Override
    protected DocumentType getDocType() {
        return docType;
    }
}

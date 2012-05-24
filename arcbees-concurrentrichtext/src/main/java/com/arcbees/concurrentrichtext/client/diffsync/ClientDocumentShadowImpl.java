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

import com.arcbees.concurrentrichtext.shared.diffsync.AbstractDocumentShadow;
import com.arcbees.concurrentrichtext.shared.diffsync.DiffHandler;
import com.arcbees.concurrentrichtext.shared.diffsync.DocumentShadow;
import com.google.inject.Inject;

public final class ClientDocumentShadowImpl extends AbstractDocumentShadow {

    private static final DocumentType docType = DocumentType.Client;
    private final DiffHandler diffHandler;

    @Inject
    public ClientDocumentShadowImpl(DiffHandler diffHandler) {
        super(diffHandler);
        this.diffHandler = diffHandler;
    }

    @Override
    protected DocumentType getDocType() {
        return docType;
    }

    @Override
    public int getTargetVersion() {
        return getServerVersion();
    }

    @Override
    public int getVersion() {
        return getClientVersion();
    }

    @Override
    protected void loadCache() {

    }

    @Override
    protected void saveCache() {

    }

    @Override
    protected DocumentShadow createNewDocument() {
        DocumentShadow cloneDocShadow;
        cloneDocShadow = new ClientDocumentShadowImpl(diffHandler);
        return cloneDocShadow;
    }

}

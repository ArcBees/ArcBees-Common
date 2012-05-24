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

package com.arcbees.concurrentrichtext.server.cache;

public class DocumentShadowCache implements CacheItem {
    private static final long serialVersionUID = -4439984988284829136L;

    private final int clientVersion;
    private final int serverVersion;
    private final String text;
    private final String id;

    public DocumentShadowCache(final String id, final String text, final int clientVersion, final int serverVersion) {
        this.id = id;
        this.text = text;
        this.clientVersion = clientVersion;
        this.serverVersion = serverVersion;
    }

    public int getClientVersion() {
        return clientVersion;
    }

    public int getServerVersion() {
        return serverVersion;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }
}

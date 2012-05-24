package com.arcbees.concurrentrichtext.server.cache;

public class DocumentShadowCache implements CacheItem {

    private static final long serialVersionUID = -4439984988284829136L;

    private final int clientVersion;
    private final int serverVersion;
    private final String text;
    private final String id;

    public DocumentShadowCache(String id, String text, int clientVersion,
                               int serverVersion) {
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

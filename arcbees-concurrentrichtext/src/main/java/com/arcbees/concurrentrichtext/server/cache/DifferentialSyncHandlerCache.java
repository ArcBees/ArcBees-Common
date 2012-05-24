package com.arcbees.concurrentrichtext.server.cache;

public class DifferentialSyncHandlerCache implements CacheItem {

    private static final long serialVersionUID = -735405699266718323L;
    private final String text;

    public DifferentialSyncHandlerCache(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}

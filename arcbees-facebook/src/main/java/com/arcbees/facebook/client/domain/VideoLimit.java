package com.arcbees.facebook.client.domain;

import com.google.gwt.core.client.JavaScriptObject;

public class VideoLimit extends JavaScriptObject {
    protected VideoLimit() {
    }

    public final native String getLength() /*-{
        return this.length;
    }-*/;

    public final native String getSize() /*-{
        return this.size;
    }-*/;
}

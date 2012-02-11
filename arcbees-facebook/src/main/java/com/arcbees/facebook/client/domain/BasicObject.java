package com.arcbees.facebook.client.domain;

import com.google.gwt.core.client.JavaScriptObject;

public class BasicObject extends JavaScriptObject {
    protected BasicObject() {
    }

    public final native String getId() /*-{
        return this.id;
    }-*/;

    public final native String getName() /*-{
        return this.name;
    }-*/;
}

package com.arcbees.facebook.client.domain;

import com.google.gwt.core.client.JavaScriptObject;

public class Work extends JavaScriptObject {
    protected Work() {
    }

    public final native String getEmployer() /*-{
        return this.employer;
    }-*/;

    public final native String getLocation() /*-{
        return this.location;
    }-*/;

    public final native String getPosition() /*-{
        return this.position;
    }-*/;

    // TODO : Find out what this returns
    public final native String getStartDate() /*-{
        return this.start_date;
    }-*/;

    // TODO : Find out what this returns
    public final native String getEndDate() /*-{
        return this.end_date;
    }-*/;
}

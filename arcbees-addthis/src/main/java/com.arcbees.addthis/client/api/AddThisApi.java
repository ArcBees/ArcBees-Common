package com.arcbees.addthis.client.api;

import com.arcbees.addthis.client.widget.AddThisPubId;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ScriptElement;
import com.google.inject.Inject;

public class AddThisApi {
    private static final String API_SCRIPT_URL = "https://s7.addthis.com/js/250/addthis_widget.js#pubid=";
    private final String pubId;

    @Inject
    public AddThisApi(@AddThisPubId String pubId) {
        this.pubId = pubId;

        injectScript();
    }

    private String getApiScriptUrl(){
        return API_SCRIPT_URL + pubId;
    }

    private void injectScript() {
        Document document = Document.get();
        ScriptElement addThisApiScript = document.createScriptElement();
        addThisApiScript.setSrc(getApiScriptUrl());
        document.getBody().appendChild(addThisApiScript);
    }

    public native void renderToolbox(String selector)/*-{
        if ($wnd.addthis) {
            $wnd.addthis.toolbox(selector);
        }
    }-*/;

    public native void renderToolbox(String selector, String url)/*-{
        if ($wnd.addthis) {
            $wnd.addthis.toolbox(selector, {}, {url:url});
        }
    }-*/;

    public native void renderCounter(String selector)/*-{
        if ($wnd.addthis) {
            $wnd.addthis.counter(selector);
        }
    }-*/;

    public native void renderCounter(String selector, String url)/*-{
        if ($wnd.addthis) {
            $wnd.addthis.counter(selector, {}, {url:url});
        }
    }-*/;
}

/*
 * Copyright 2013 ArcBees Inc.
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

package com.arcbees.facebook.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

public class JavaScriptFacebook extends AbstractFacebook {
    private static final String FB_ROOT = "fb-root";
    private static final String FB_SCRIPT_SRC = Window.Location.getProtocol()
            + "//connect.facebook.net/en_US/all.js";
    private static final String FB_SCRIPT_TYPE = "text/javascript";

    @Override
    public void injectFacebookApi(final FacebookCallback facebookCallback) {
        Element firstElement = Document.get().getBody().getFirstChildElement();

        Element fbRoot = Document.get().createDivElement();
        fbRoot.setId(FB_ROOT);

        firstElement.getParentNode().insertBefore(fbRoot, firstElement);

        ScriptElement fbScript = Document.get().createScriptElement();
        fbScript.setSrc(FB_SCRIPT_SRC);
        fbScript.setType(FB_SCRIPT_TYPE);

        fbRoot.getParentNode().insertAfter(fbScript, fbRoot);

        Timer ensureFbIsLoaded = new Timer() {
            @Override
            public void run() {
                if (isLoaded()) {
                    facebookCallback.onSuccess();

                    cancel();
                }
            }
        };

        ensureFbIsLoaded.scheduleRepeating(100);
    }

    @Override
    public native void init(String appId,
                            boolean status,
                            boolean cookie,
                            boolean xfbml) /*-{
        $wnd.FB.init({
            'appId': appId,
            'status': status,
            'cookie': cookie,
            'xfbml': xfbml
        });
    }-*/;
}

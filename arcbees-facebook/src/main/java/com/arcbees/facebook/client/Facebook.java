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

package com.arcbees.facebook.client;

import com.arcbees.facebook.client.domain.AuthResponse;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ScriptElement;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

import javax.inject.Inject;

public class Facebook {
    private static final String FB_ROOT = "fb-root";
    private static final String FB_SCRIPT_SRC = Window.Location.getProtocol()
            + "//connect.facebook.net/en_US/all.js";
    private static final String FB_SCRIPT_TYPE = "text/javascript";

    private final Api api;

    @Inject
    public Facebook(final Api api) {
        this.api = api;
    }

    public void injectFacebookApi(final Callback callback) {
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
                    callback.onSuccess();

                    cancel();
                }
            }
        };

        ensureFbIsLoaded.scheduleRepeating(100);
    }

    /**
     * @see <a href="http://developers.facebook.com/docs/reference/javascript/FB.init/">FB.init</a>
     */
    public native void init(String appId, boolean status, boolean cookie, boolean xfbml, boolean oauth) /*-{
        $wnd.FB.init({
            'appId':appId,
            'status':status,
            'cookie':cookie,
            'xfbml':xfbml,
            'oauth':oauth
        });
    }-*/;

    /**
     * @see <a href="http://developers.facebook.com/docs/reference/javascript/FB.getLoginStatus/">FB.getLoginSatus</a>
     */
    public native AuthResponse getLoginStatus(AsyncCallback<AuthResponse> callback) /*-{
        var instance = callback;

        $wnd.FB.getLoginStatus(function (response) {
            return instance.@com.arcbees.facebook.client.AsyncCallback::onSuccess(Ljava/lang/Object;)(response.authResponse);
        });
    }-*/;

    /**
     * @see <a href="http://developers.facebook.com/docs/reference/javascript/FB.login/">FB.login</a>
     */
    public native void login(String scopeValue, AsyncCallback<AuthResponse> callback) /*-{
        var instance = callback;

        $wnd.FB.login(function (response) {
            if (response.status == @com.arcbees.facebook.client.Status::Connected) {
                instance.@com.arcbees.facebook.client.AsyncCallback::onSuccess(Ljava/lang/Object;)(response.authResponse);
            } else {
                instance.@com.arcbees.facebook.client.AsyncCallback::onFailure(Ljava/lang/Object;)(response.authResponse);
            }
        }, {
            scope:scopeValue
        });
    }-*/;

    /**
     * @see <a href="http://developers.facebook.com/docs/reference/javascript/FB.logout/">FB.logout</a>
     */
    public native void logout(Callback callback) /*-{
        var instance = callback;

        $wnd.FB.logout(function (response) {
            instance.@com.arcbees.facebook.client.Callback::onSuccess()();
        });
    }-*/;

    /**
     * @see <a href="https://developers.facebook.com/docs/reference/javascript/FB.getAuthResponse/">FB.getAuthResponse</a>
     */
    public native AuthResponse getAuthResponse() /*-{
        return $wnd.FB.getAuthResponse();
    }-*/;

    public Api api() {
        return api;
    }

    protected native boolean isLoaded() /*-{
        if ($wnd.FB) {
            return true;
        } else {
            return false;
        }
    }-*/;
}

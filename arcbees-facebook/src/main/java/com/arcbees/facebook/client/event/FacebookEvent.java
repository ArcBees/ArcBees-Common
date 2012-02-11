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

package com.arcbees.facebook.client.event;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FacebookEvent {
    public native void subscribe(String event, AsyncCallback<JavaScriptObject> callback) /*-{
        var instance = this;

        $wnd.FB.Event.subscribe(
                event, function (response) {
                    instance.@com.arcbees.facebook.client.event.FacebookEvent::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback, response);
                });
    }-*/;

    public native void unsubscribe(String event, AsyncCallback<JavaScriptObject> callback) /*-{
        var instance = this;

        $wnd.FB.Event.unsubscribe(
                event, function (response) {
                    instance.@com.arcbees.facebook.client.event.FacebookEvent::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Lcom/google/gwt/core/client/JavaScriptObject;)(callback, response);
                });
    }-*/;

    protected void callbackSuccess(AsyncCallback<JavaScriptObject> callback, JavaScriptObject obj) {
        callback.onSuccess(obj);
    }
}

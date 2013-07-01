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

import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AbstractFacebook implements Facebook {
    @Override
    public native void getLoginStatus(AsyncCallback<AuthResponse> callback) /*-{
        var instance = this;

        $wnd.FB.getLoginStatus(function (response) {
            instance.@com.arcbees.facebook.client.AbstractFacebook::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Ljava/lang/Object;)(callback, response);
        });
    }-*/;

    @Override
    public native void login(String scopeValue,
                             AsyncCallback<AuthResponse> callback) /*-{
        var instance = this;

        $wnd.FB.login(
            function (response) {
                if (response.status == @com.arcbees.facebook.client.Status::Connected) {
                    instance.@com.arcbees.facebook.client.AbstractFacebook::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Ljava/lang/Object;)(callback, response);
                } else {
                    instance.@com.arcbees.facebook.client.AbstractFacebook::callbackFailure(Lcom/google/gwt/user/client/rpc/AsyncCallback;Ljava/lang/Object;)(callback, response);
                }
            }, {
                scope: scopeValue
            });
    }-*/;

    @Override
    public native void logout(AsyncCallback<AuthResponse> callback) /*-{
        var instance = this;

        $wnd.FB.logout(function (response) {
            instance.@com.arcbees.facebook.client.AbstractFacebook::callbackSuccess(Lcom/google/gwt/user/client/rpc/AsyncCallback;Ljava/lang/Object;)(callback, response);;
        });
    }-*/;

    @Override
    public native boolean isLoaded() /*-{
        if ($wnd.FB) {
            return true;
        } else {
            return false;
        }
    }-*/;

    protected <T> void callbackSuccess(AsyncCallback<T> callback,
                                       T response) {
        callback.onSuccess(response);
    }

    protected <T> void callbackFailure(AsyncCallback<T> callback,
                                       T response) {
        callback.onFailure(new Throwable(""));
    }
}

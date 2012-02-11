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

package com.arcbees.facebook.client.domain;

import com.arcbees.facebook.client.Status;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Wrapper for the JSON representation of authResponse.
 *
 * <pre>{@code
    authResponse: {
        accessToken: '...',
        expiresIn:'...',
        signedRequest:'...',
        userID:'...'
    }}</pre>
 */
public class AuthResponse extends JavaScriptObject {
    protected AuthResponse() {
    }

    public final native String getAccessToken() /*-{
        if (this.authResponse) {
            return this.accessToken;
        } else {
            return "";
        }
    }-*/;

    public final native String getExpiresIn() /*-{
        if (this.authResponse) {
            return this.expiresIn;
        } else {
            return "";
        }
    }-*/;

    public final native String getSignedRequest() /*-{
        if (this.authResponse) {
            return this.signedRequest;
        } else {
            return "";
        }
    }-*/;

    public final native String getUserId() /*-{
        if (this.authResponse) {
            return this.userID;
        } else {
            return "";
        }
    }-*/;
}

/*
 * Copyright (C) 2011 Google Inc.
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

package com.google.gwt.appengine.channel.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * An error encountered by a {@link Socket}, passed to the
 * {@link SocketListener}.
 */
public final class SocketError extends JavaScriptObject {
    protected SocketError() {
    }

    /**
     * Returns a short description of the error encountered.
     */
    public final native String getDescription() /*-{
        return this.description;
    }-*/;

    /**
     * Returns the HTTP error code corresponding to the error encountered.
     */
    public final native int getCode() /*-{
        return this.code;
    }-*/;
}

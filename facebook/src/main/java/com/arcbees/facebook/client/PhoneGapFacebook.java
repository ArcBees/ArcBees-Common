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

public class PhoneGapFacebook extends AbstractFacebook {
    @Override
    public void injectFacebookApi(FacebookCallback facebookCallback) {
        // Facebook SDK is already added locally from index.html
        facebookCallback.onSuccess();
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
            'xfbml': xfbml,
            'nativeInterface': $wnd.CDV.FB,
            'useCachedDialogs': false
        });
    }-*/;
}

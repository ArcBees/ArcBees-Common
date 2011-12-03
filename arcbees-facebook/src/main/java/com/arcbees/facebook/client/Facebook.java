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

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface Facebook {
  /**
   * @see {@link http://developers.facebook.com/docs/reference/javascript/FB.init/}
   */
  void init(String appId, boolean status, boolean cookie, boolean xfbml);

  /**
   * @see {@link http://developers.facebook.com/docs/reference/javascript/FB.getLoginStatus/}
   */
  void getLoginStatus(AsyncCallback<AuthResponse> callback);

  /**
   * @see {@link http://developers.facebook.com/docs/reference/javascript/FB.login/}
   */
  void login(String scopeValue, AsyncCallback<AuthResponse> callback);

  /**
   * @see {@link http://developers.facebook.com/docs/reference/javascript/FB.logout/}
   */
  void logout(AsyncCallback<AuthResponse> callback);

  void injectFacebookApi(FacebookCallback facebookCallback);

  boolean isLoaded();
}

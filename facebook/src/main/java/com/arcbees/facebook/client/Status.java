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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

public enum Status {
  Connected("connected"), NotConnected("notConnected"), Unknown("unknown");

  private static final String JSON_STATUS = "status";

  private final String statusName;

  private Status(String statusName) {
    this.statusName = statusName;
  }

  @Override
  public String toString() {
    return statusName;
  }

  public static Status valueOf(JavaScriptObject jsObject) {
    JSONObject json = new JSONObject(jsObject);

    JSONString returnedStatus = json.get(JSON_STATUS).isString();

    for (Status status : Status.values()) {
      if (status.toString().equals(returnedStatus.stringValue())) {
        return status;
      }
    }

    return Status.Unknown;
  }
}

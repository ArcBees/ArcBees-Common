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

package com.arcbees.concurrentrichtext.shared.collaborativetext;

public class SendEditsResultRestore implements SendEditsResult {

    private static final long serialVersionUID = -5269978730082053208L;
    private String text;
    private int clientVersion;
    private int serverVersion;

    @SuppressWarnings("unused")
    private SendEditsResultRestore() {
    }

    public SendEditsResultRestore(String text, int clientVersion,
                                  int serverVersion) {
        this.text = text;
        this.clientVersion = clientVersion;
        this.serverVersion = serverVersion;
    }

    public String getText() {
        return text;
    }

    public int getClientVersion() {
        return clientVersion;
    }

    public int getServerVersion() {
        return serverVersion;
    }

    @Override
    public ResultType getResultType() {
        return ResultType.RESTORE;
    }

}

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

package com.arcbees.concurrentrichtext.server.diffsync;

public class PatchApplyException extends Exception {
    private final int version;
    private final int targetVersion;
    private final String text;

    private static final long serialVersionUID = 20506879828452210L;

    public PatchApplyException(final String text, final int targetVersion, final int version) {
        this.text = text;
        this.targetVersion = targetVersion;
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public int getTargetVersion() {
        return targetVersion;
    }

    public String getRestoreText() {
        return text;
    }
}

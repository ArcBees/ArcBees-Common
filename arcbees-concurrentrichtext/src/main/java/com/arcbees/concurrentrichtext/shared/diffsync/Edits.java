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

package com.arcbees.concurrentrichtext.shared.diffsync;

import java.io.Serializable;
import java.util.LinkedList;

public class Edits implements Serializable {
    private static final long serialVersionUID = -761346683330492629L;
    private LinkedList<Diff> diffs;
    private int targetVersion;
    private int version;

    @SuppressWarnings("unused")
    Edits() {
    }

    public Edits(int version, int targetVersion, final LinkedList<Diff> diffs) {
        this.diffs = diffs;
        this.targetVersion = targetVersion;
        this.version = version;
    }

    public boolean hasEdits() {
        return diffs.size() > 0;
    }

    public int getVersion() {
        return version;
    }

    public int getTargetVersion() {
        return targetVersion;
    }

    public LinkedList<Diff> getDiffs() {
        return diffs;
    }
}

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

public interface DocumentShadow extends Cloneable {
    ApplyEditsResult applyEdits(Edits edits);

    int getTargetVersion();

    int getVersion();

    void updateText(String newText);

    String getText();

    void restore(int clientVersion, int serverVersion, String text);

    void restoreFromBackup();

    void restoreFromCache(String id, int clientVersion, int serverVersion, String text);

    void initialize(String id, String baseText);

    boolean versionsMatches(Edits edits);

    boolean versionInPast(Edits edits);
}

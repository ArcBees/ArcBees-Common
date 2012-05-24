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

import com.arcbees.concurrentrichtext.client.collaborativetext.CursorOffset;
import com.arcbees.concurrentrichtext.shared.diffsync.Diff.Operation;
import com.google.inject.Inject;

import java.util.LinkedList;

public class DiffHandler {

    private DiffMatchPatch diffPatch;

    @Inject
    public DiffHandler(DiffMatchPatch diffPatch) {
        this.diffPatch = diffPatch;
    }

    public Edits getEdits(DocumentShadow oldDoc, String newText) {
        Edits edits;
        LinkedList<Diff> diffs = diffPatch.diff_main(oldDoc.getText(), newText,
                                                     false);

        if (diffs.size() == 1) {
            Diff diff = diffs.get(0);
            if (Operation.EQUAL.equals(diff.operation)) {
                diffs = new LinkedList<Diff>();
            }
        }

        edits = new Edits(oldDoc.getVersion(), oldDoc.getTargetVersion(), diffs);

        return edits;
    }

    private LinkedList<Patch> getPatches(String oldText, LinkedList<Diff> diffs) {
        return diffPatch.patch_make(oldText, diffs);
    }

    public PatchResultOffset applyEdits(Edits edits, CursorOffset cursor,
                                        String text) {
        LinkedList<Patch> patches = getPatches(text, edits.getDiffs());
        PatchResultOffset patchResult = diffPatch.patch_apply_offsets(patches,
                                                                      cursor, text);

        return patchResult;
    }

    public PatchResult applyEdits(Edits edits, String text) {
        PatchResult patchResult;
        LinkedList<Patch> patches = getPatches(text, edits.getDiffs());
        Object[] result = diffPatch.patch_apply(patches, text);
        patchResult = new PatchResult((String) result[0], (boolean[]) result[1]);

        return patchResult;
    }
}

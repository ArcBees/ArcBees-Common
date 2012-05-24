package com.arcbees.concurrentrichtext.shared.diffsync;

import com.arcbees.concurrentrichtext.client.collaborativetext.CursorOffset;

public class PatchResultOffset extends PatchResult {

    private final CursorOffset cursorOffset;

    public PatchResultOffset(String newText, boolean patchesResults,
                             CursorOffset cursorOffset) {
        super(newText, new boolean[]{patchesResults});
        this.cursorOffset = cursorOffset;
    }

    public CursorOffset getCursorOffset() {
        return cursorOffset;
    }

}

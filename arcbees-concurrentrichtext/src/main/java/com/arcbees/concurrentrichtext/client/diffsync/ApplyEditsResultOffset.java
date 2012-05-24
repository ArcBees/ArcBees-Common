package com.arcbees.concurrentrichtext.client.diffsync;

import com.arcbees.concurrentrichtext.client.collaborativetext.CursorOffset;

public class ApplyEditsResultOffset {

    private final boolean hasAppliedEdits;
    private final CursorOffset cursorOffset;

    public ApplyEditsResultOffset(boolean hasAppliedEdits,
                                  CursorOffset cursorOffset) {
        this.hasAppliedEdits = hasAppliedEdits;
        this.cursorOffset = cursorOffset;
    }

    public boolean hasAppliedEdits() {
        return hasAppliedEdits;
    }

    public CursorOffset getCursorOffset() {
        return cursorOffset;
    }

}

package com.arcbees.concurrentrichtext.client.collaborativetext;

public class CursorOffset {

    private final int cursorPosition;
    private final int selectionStart;
    private final int selectionEnd;

    public CursorOffset(int cursorPosition, int selectionStart, int selectionEnd) {
        this.cursorPosition = cursorPosition;
        this.selectionStart = selectionStart;
        this.selectionEnd = selectionEnd;
    }

    public int getCursorPosition() {
        return cursorPosition;
    }

    public int getSelectionStart() {
        return selectionStart;
    }

    public int getSelectionEnd() {
        return selectionEnd;
    }

}

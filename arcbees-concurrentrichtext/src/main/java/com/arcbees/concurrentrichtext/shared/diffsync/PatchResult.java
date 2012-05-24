package com.arcbees.concurrentrichtext.shared.diffsync;

public class PatchResult {

    private final String newText;
    private final boolean[] patchesResults;

    public PatchResult(String newText, boolean[] patchesResults) {
        this.newText = newText;
        this.patchesResults = patchesResults;
    }

    public String getNewText() {
        return newText;
    }

    public boolean[] getPatchesResults() {
        return patchesResults;
    }

}

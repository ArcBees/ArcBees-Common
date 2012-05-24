package com.arcbees.concurrentrichtext.client.diffsync;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

import com.arcbees.concurrentrichtext.client.collaborativetext.CursorOffset;
import com.arcbees.concurrentrichtext.shared.diffsync.Diff;
import com.arcbees.concurrentrichtext.shared.diffsync.Diff.Operation;
import com.arcbees.concurrentrichtext.shared.diffsync.DiffMatchPatch;
import com.arcbees.concurrentrichtext.shared.diffsync.Patch;
import com.arcbees.concurrentrichtext.shared.diffsync.PatchResultOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ClientDiffMatchPatch implements DiffMatchPatch {

    private static final int DiffOperationDELETE = -1;
    private static final int DiffOperationINSERT = 1;
    private static final int DiffOperationEQUAL = 0;

    public static JavaScriptObject DiffsToJSO(LinkedList<Diff> diffs) {
        JSONArray jsonDiffs = DiffsToJSONObject(diffs);

        return jsonDiffs.getJavaScriptObject();
    }

    public static JSONArray DiffsToJSONObject(LinkedList<Diff> diffs) {
        JSONArray jsonDiffs = new JSONArray();

        int i = 0;
        for (Diff diff : diffs) {
            JSONArray jsonDiff = DiffToJSON(diff);
            jsonDiffs.set(i, jsonDiff);
            i++;
        }

        return jsonDiffs;
    }

    private static JSONArray DiffToJSON(Diff diff) {
        JSONArray array = new JSONArray();
        JSONNumber number = new JSONNumber(getOperation(diff.operation));
        JSONString string = new JSONString(diff.text);

        array.set(0, number);
        array.set(1, string);

        return array;
    }

    private static JavaScriptObject PatchesToJSO(LinkedList<Patch> patches) {
        ClientPatchHelper clientPatchHelper = new ClientPatchHelper();
        return clientPatchHelper.getPatchesAsJSO(patches);
    }

    private static int getOperation(Diff.Operation operation) {
        switch (operation) {
            case DELETE:
                return DiffOperationDELETE;
            case EQUAL:
                return DiffOperationEQUAL;
            case INSERT:
                return DiffOperationINSERT;
        }

        return DiffOperationEQUAL;
    }

    private static Diff.Operation getOperation(int operation) {
        switch (operation) {
            case DiffOperationDELETE:
                return Diff.Operation.DELETE;
            case DiffOperationEQUAL:
                return Diff.Operation.EQUAL;
            case DiffOperationINSERT:
                return Diff.Operation.INSERT;
        }

        return Diff.Operation.EQUAL;
    }

    private static LinkedList<Diff> getDiffsList(JavaScriptObject jso) {
        LinkedList<Diff> diffsList = new LinkedList<Diff>();
        JSONArray jsonArray = new JSONArray(jso);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONArray diff = (JSONArray) jsonArray.get(i);
            Diff.Operation op = getOperation((int) diff.get(0).isNumber().doubleValue());
            String diffText = diff.get(1).isString().stringValue();
            diffsList.add(new Diff(op, diffText));
        }
        return diffsList;
    }

    private static LinkedList<Patch> getPatchesList(JavaScriptObject jso) {
        LinkedList<Patch> patchesList = new LinkedList<Patch>();
        JSONArray jsonArray = new JSONArray(jso);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonPatch = (JSONObject) jsonArray.get(i);
            Patch patch = new Patch();
            JSONArray diffs = (JSONArray) jsonPatch.get("diffs");
            patch.diffs = getDiffsList(diffs.getJavaScriptObject());
            patch.start1 = (int) jsonPatch.get("start1").isNumber().doubleValue();
            patch.start2 = (int) jsonPatch.get("start2").isNumber().doubleValue();
            patch.length1 = (int) jsonPatch.get("length1").isNumber().doubleValue();
            patch.length2 = (int) jsonPatch.get("length2").isNumber().doubleValue();
            patchesList.add(patch);
        }
        return patchesList;
    }

    @Override
    public PatchResultOffset patch_apply_offsets(LinkedList<Patch> patches,
                                                 CursorOffset cursorOffset, String text) {
        if (patches.isEmpty()) {
            return new PatchResultOffset(text, true, cursorOffset);
        }

        int Match_MaxBits = 32;
        float Patch_DeleteThreshold = 0.5f;

        List<Integer> offsets = new ArrayList<Integer>();
        offsets.add(cursorOffset.getCursorPosition());
        offsets.add(cursorOffset.getSelectionStart());
        offsets.add(cursorOffset.getSelectionEnd());

        // Deep copy the patches so that no changes are made to originals.
        // patches = dmp.patch_deepCopy(patches);

        // Lock the user out of the document for a split second while patching.
        // this.textComponent.setEditable(false);
        try {
            String nullPadding = patch_addPadding(patches);
            text = nullPadding + text + nullPadding;
            patches = patch_splitMaxNative(patches);

            int x = 0;
            // delta keeps track of the offset between the expected and actual
            // location
            // of the previous patch. If there are patches expected at positions 10
            // and
            // 20, but the first patch was found at 12, delta is 2 and the second
            // patch
            // has an effective expected position of 22.
            int delta = 0;
            for (Patch aPatch : patches) {
                int expected_loc = aPatch.start2 + delta;
                String text1 = diff_text1(aPatch.diffs);
                int start_loc;
                int end_loc = -1;
                if (text1.length() > Match_MaxBits) {
                    // patch_splitMax will only provide an oversized pattern in the case
                    // of
                    // a monster delete.
                    start_loc = match_main(text, text1.substring(0, Match_MaxBits),
                                           expected_loc);
                    if (start_loc != -1) {
                        end_loc = match_main(text,
                                             text1.substring(text1.length() - Match_MaxBits), expected_loc
                                                                                              + text1.length() -
                                                                                              Match_MaxBits);
                        if (end_loc == -1 || start_loc >= end_loc) {
                            // Can't find valid trailing context. Drop this patch.
                            start_loc = -1;
                        }
                    }
                } else {
                    start_loc = match_main(text, text1, expected_loc);
                }
                if (start_loc == -1) {
                    // No match found. :(
                    // Subtract the delta for this failed patch from subsequent patches.
                    delta -= aPatch.length2 - aPatch.length1;
                } else {
                    // Found a match. :)
                    delta = start_loc - expected_loc;
                    String text2;
                    if (end_loc == -1) {
                        text2 = text.substring(start_loc,
                                               Math.min(start_loc + text1.length(), text.length()));
                    } else {
                        text2 = text.substring(start_loc,
                                               Math.min(end_loc + Match_MaxBits, text.length()));
                    }
                    // Run a diff to get a framework of equivalent indices.
                    LinkedList<Diff> diffs = diff_main(text1, text2, false);
                    if (text1.length() > Match_MaxBits
                        && diff_levenshtein(diffs) / (float) text1.length() > Patch_DeleteThreshold) {
                        // The end points match, but the content is unacceptably bad.
                    } else {
                        int index1 = 0;
                        for (Diff aDiff : aPatch.diffs) {
                            if (aDiff.operation != Operation.EQUAL) {
                                int index2 = diff_xIndex(diffs, index1);
                                if (aDiff.operation == Operation.INSERT) {
                                    // Insertion
                                    text = text.substring(0, start_loc + index2) + aDiff.text
                                           + text.substring(start_loc + index2);
                                    for (int i = 0; i < offsets.size(); i++) {
                                        if (offsets.get(i) + nullPadding.length() > start_loc
                                                                                    + index2) {
                                            offsets.set(i, offsets.get(i) + aDiff.text.length());
                                        }
                                    }
                                } else if (aDiff.operation == Operation.DELETE) {
                                    // Deletion
                                    int del_start = start_loc + index2;
                                    int del_end = start_loc
                                                  + diff_xIndex(diffs, index1 + aDiff.text.length());
                                    text = text.substring(0, del_start) + text.substring(del_end);
                                    for (int i = 0; i < offsets.size(); i++) {
                                        if (offsets.get(i) + nullPadding.length() > del_start) {
                                            if (offsets.get(i) + nullPadding.length() < del_end) {
                                                offsets.set(i, del_start - nullPadding.length());
                                            } else {
                                                offsets.set(i, offsets.get(i) - (del_end - del_start));
                                            }
                                        }
                                    }
                                }
                            }
                            if (aDiff.operation != Operation.DELETE) {
                                index1 += aDiff.text.length();
                            }
                        }
                    }
                }
                x++;
            }
            // Strip the padding off.
            text = text.substring(nullPadding.length(),
                                  text.length() - nullPadding.length());
        } finally {
        }
        CursorOffset offset = new CursorOffset(offsets.get(0), offsets.get(1),
                                               offsets.get(2));
        return new PatchResultOffset(text, true, offset);
    }

    @Override
    public LinkedList<Diff> diff_main(String text1, String text2) {
        JavaScriptObject diffs = n_diff_main(text1, text2, true);
        LinkedList<Diff> diffsList = getDiffsList(diffs);
        return diffsList;
    }

    private native JavaScriptObject n_diff_main(String text1, String text2,
                                                boolean checklines)/*-{
        var dmp = new $wnd.diff_match_patch();
        var d = dmp.diff_main(text1, text2, checklines);
        dmp.diff_cleanupSemantic(d);
        return d;
    }-*/;

    @Override
    public LinkedList<Diff> diff_main(String text1, String text2,
                                      boolean checklines) {
        JavaScriptObject diffs = n_diff_main(text1, text2, checklines);
        LinkedList<Diff> diffsList = getDiffsList(diffs);
        return diffsList;
    }

    @Override
    public int diff_commonPrefix(String text1, String text2) {
        throw new Error("NotImplemented");
    }

    @Override
    public int diff_commonSuffix(String text1, String text2) {
        throw new Error("NotImplemented");
    }

    @Override
    public void diff_cleanupSemantic(LinkedList<Diff> diffs) {
        throw new Error("NotImplemented");
    }

    private native void n_diff_cleanupSemantic(Diff[] diffs)/*-{
        var dmp = new $wnd.diff_match_patch();
        dmp.diff_cleanupSemantic(diffs);
    }-*/;

    @Override
    public void diff_cleanupSemanticLossless(LinkedList<Diff> diffs) {
        throw new Error("NotImplemented");
    }

    @Override
    public void diff_cleanupEfficiency(LinkedList<Diff> diffs) {
        throw new Error("NotImplemented");
    }

    @Override
    public void diff_cleanupMerge(LinkedList<Diff> diffs) {
        throw new Error("NotImplemented");
    }

    @Override
    public int diff_xIndex(LinkedList<Diff> diffs, int loc) {
        JavaScriptObject jsoDiffs = DiffsToJSO(diffs);
        return n_diff_xIndex(jsoDiffs, loc);
    }

    private native int n_diff_xIndex(JavaScriptObject diffs, int loc) /*-{
        var dmp = new $wnd.diff_match_patch();
        return dmp.diff_xIndex(diffs, loc);
    }-*/;

    @Override
    public String diff_prettyHtml(LinkedList<Diff> diffs) {
        throw new Error("NotImplemented");
    }

    @Override
    public String diff_text1(LinkedList<Diff> diffs) {
        JavaScriptObject jsoDiffs = DiffsToJSO(diffs);
        return n_diff_text1(jsoDiffs);
    }

    private native String n_diff_text1(JavaScriptObject diffs)/*-{
        var dmp = new $wnd.diff_match_patch();
        return dmp.diff_text1(diffs);
    }-*/;

    @Override
    public String diff_text2(LinkedList<Diff> diffs) {
        throw new Error("NotImplemented");
    }

    @Override
    public int diff_levenshtein(LinkedList<Diff> diffs) {
        JavaScriptObject jsoDiffs = DiffsToJSO(diffs);
        return n_diff_levenshtein(jsoDiffs);
    }

    private native int n_diff_levenshtein(JavaScriptObject diffs)/*-{
        var dmp = new $wnd.diff_match_patch();
        return dmp.diff_levenshtein(diffs);
    }-*/;

    @Override
    public String diff_toDelta(LinkedList<Diff> diffs) {
        throw new Error("NotImplemented");
    }

    @Override
    public LinkedList<Diff> diff_fromDelta(String text1, String delta)
            throws IllegalArgumentException {
        throw new Error("NotImplemented");
    }

    @Override
    public int match_main(String text, String pattern, int loc) {
        return n_match_main(text, pattern, loc);
    }

    private native int n_match_main(String text, String pattern, int loc)/*-{
        var dmp = new $wnd.diff_match_patch();
        return dmp.match_main(text, pattern, loc);
    }-*/;

    @Override
    public LinkedList<Patch> patch_make(String text1, String text2) {
        Patch[] patches = n_patch_make(text1, text2);
        LinkedList<Patch> patchesList = new LinkedList<Patch>(
                Arrays.asList(patches));
        return patchesList;
    }

    private native Patch[] n_patch_make(String text1, String text2)/*-{
        var dmp = new $wnd.diff_match_patch();
        var d = dmp.patch_make(text1, text2);
        return d;
    }-*/;

    @Override
    public LinkedList<Patch> patch_make(LinkedList<Diff> diffs) {
        Patch[] patches = n_patch_make((Diff[]) diffs.toArray());
        LinkedList<Patch> patchesList = new LinkedList<Patch>(
                Arrays.asList(patches));
        return patchesList;
    }

    private native Patch[] n_patch_make(Diff[] diffs)/*-{
        var dmp = new $wnd.diff_match_patch();
        var d = dmp.patch_make(diffs);
        return d;
    }-*/;

    @Override
    public LinkedList<Patch> patch_make(String text1, String text2,
                                        LinkedList<Diff> diffs) {
        JavaScriptObject patches = n_patch_make(text1, DiffsToJSO(diffs));
        LinkedList<Patch> patchesList = getPatchesList(patches);
        return patchesList;
    }

    private native JavaScriptObject n_patch_make(String text1,
                                                 JavaScriptObject diffs)/*-{
        var dmp = new $wnd.diff_match_patch();
        var d = dmp.patch_make(text1, diffs);
        return d;
    }-*/;

    @Override
    public LinkedList<Patch> patch_make(String text1, LinkedList<Diff> diffs) {
        JavaScriptObject patches = n_patch_make(text1, DiffsToJSO(diffs));
        LinkedList<Patch> patchesList = getPatchesList(patches);
        return patchesList;
    }

    @Override
    public LinkedList<Patch> patch_deepCopy(LinkedList<Patch> patches) {
        throw new Error("NotImplemented");
    }

    @Override
    public Object[] patch_apply(LinkedList<Patch> patches, String text) {
        JavaScriptObject patchResult = n_patch_apply(PatchesToJSO(patches), text);
        JSONArray obj = new JSONArray(patchResult);
        Object[] object = {
                obj.get(0).isString().stringValue(), getBooleans(obj.get(1).isArray())};
        return object;
    }

    private boolean[] getBooleans(JSONArray array) {
        int size = array.size();
        boolean[] bools = new boolean[size];
        for (int i = 0; i < size; i++) {
            bools[i] = array.get(i).isBoolean().booleanValue();
        }

        return bools;
    }

    private native JavaScriptObject n_patch_apply(JavaScriptObject patches,
                                                  String text)/*-{
        var dmp = new $wnd.diff_match_patch();
        var d = dmp.patch_apply(patches, text);
        return d;
    }-*/;

    @Override
    public String patch_addPadding(LinkedList<Patch> patches) {
        JavaScriptObject jsoPatches = PatchesToJSO(patches);
        return n_patch_addPadding(jsoPatches);
    }

    private native String n_patch_addPadding(JavaScriptObject patches)/*-{
        var dmp = new $wnd.diff_match_patch();
        return dmp.patch_addPadding(patches);
    }-*/;

    @Override
    public void patch_splitMax(LinkedList<Patch> patches) {
        throw new Error("NotImplemented");

    }

    private LinkedList<Patch> patch_splitMaxNative(LinkedList<Patch> patches) {
        JavaScriptObject jsoPatches = PatchesToJSO(patches);
        JavaScriptObject jsoReturn = n_patch_splitMaxNative(jsoPatches);
        return getPatchesList(jsoReturn);
    }

    private native JavaScriptObject n_patch_splitMaxNative(
            JavaScriptObject patches)/*-{
        var dmp = new $wnd.diff_match_patch();
        dmp.patch_splitMax(patches);
        return patches;
    }-*/;

    @Override
    public String patch_toText(List<Patch> patches) {
        throw new Error("NotImplemented");
    }

    @Override
    public List<Patch> patch_fromText(String textline)
            throws IllegalArgumentException {
        throw new Error("NotImplemented");
    }

}

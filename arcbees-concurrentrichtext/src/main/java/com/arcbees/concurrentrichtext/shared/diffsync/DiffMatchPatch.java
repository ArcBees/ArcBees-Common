package com.arcbees.concurrentrichtext.shared.diffsync;

import com.arcbees.concurrentrichtext.client.collaborativetext.CursorOffset;

import java.util.LinkedList;
import java.util.List;

public interface DiffMatchPatch {

    LinkedList<Diff> diff_main(String text1, String text2);

    LinkedList<Diff> diff_main(String text1, String text2, boolean checklines);

    int diff_commonPrefix(String text1, String text2);

    int diff_commonSuffix(String text1, String text2);

    void diff_cleanupSemantic(LinkedList<Diff> diffs);

    void diff_cleanupSemanticLossless(LinkedList<Diff> diffs);

    void diff_cleanupEfficiency(LinkedList<Diff> diffs);

    void diff_cleanupMerge(LinkedList<Diff> diffs);

    int diff_xIndex(LinkedList<Diff> diffs, int loc);

    String diff_prettyHtml(LinkedList<Diff> diffs);

    String diff_text1(LinkedList<Diff> diffs);

    String diff_text2(LinkedList<Diff> diffs);

    int diff_levenshtein(LinkedList<Diff> diffs);

    String diff_toDelta(LinkedList<Diff> diffs);

    LinkedList<Diff> diff_fromDelta(String text1, String delta)
            throws IllegalArgumentException;

    int match_main(String text, String pattern, int loc);

    LinkedList<Patch> patch_make(String text1, String text2);

    LinkedList<Patch> patch_make(LinkedList<Diff> diffs);

    LinkedList<Patch> patch_make(String text1, String text2,
                                 LinkedList<Diff> diffs);

    LinkedList<Patch> patch_make(String text1, LinkedList<Diff> diffs);

    LinkedList<Patch> patch_deepCopy(LinkedList<Patch> patches);

    Object[] patch_apply(LinkedList<Patch> patches, String text);

    String patch_addPadding(LinkedList<Patch> patches);

    void patch_splitMax(LinkedList<Patch> patches);

    String patch_toText(List<Patch> patches);

    List<Patch> patch_fromText(String textline) throws IllegalArgumentException;

    PatchResultOffset patch_apply_offsets(LinkedList<Patch> patches,
                                          CursorOffset cursorOffset, String text);

}

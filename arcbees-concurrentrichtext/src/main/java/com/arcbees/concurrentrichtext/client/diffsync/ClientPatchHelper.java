package com.arcbees.concurrentrichtext.client.diffsync;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

import com.arcbees.concurrentrichtext.shared.diffsync.Patch;
import java.util.LinkedList;

public class ClientPatchHelper {

    public ClientPatchHelper() {
    }

    private JSONObject getPatchAsJSON(Patch patch) {
        JSONValue diffs = ClientDiffMatchPatch.DiffsToJSONObject(patch.diffs);

        JSONObject jsonPatch = new JSONObject();
        jsonPatch.put("diffs", diffs);
        jsonPatch.put("start1", new JSONNumber(patch.start1));
        jsonPatch.put("start2", new JSONNumber(patch.start2));
        jsonPatch.put("length1", new JSONNumber(patch.length1));
        jsonPatch.put("length2", new JSONNumber(patch.length2));

        return jsonPatch;
    }

    public JavaScriptObject getPatchesAsJSO(LinkedList<Patch> patches) {
        JSONArray jsonPatches = new JSONArray();
        int i = 0;
        for (Patch patch : patches) {
            jsonPatches.set(i, getPatchAsJSON(patch));
            i++;
        }

        return jsonPatches.getJavaScriptObject();
    }

}

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

package com.arcbees.concurrentrichtext.client.diffsync;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

import com.arcbees.concurrentrichtext.shared.diffsync.Patch;
import java.util.LinkedList;

public class ClientPatchHelper {
    public JavaScriptObject getPatchesAsJSO(LinkedList<Patch> patches) {
        JSONArray jsonPatches = new JSONArray();
        int i = 0;
        for (Patch patch : patches) {
            jsonPatches.set(i, getPatchAsJSON(patch));
            i++;
        }

        return jsonPatches.getJavaScriptObject();
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
}

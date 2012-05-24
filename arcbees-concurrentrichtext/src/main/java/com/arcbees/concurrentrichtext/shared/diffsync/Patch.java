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

import com.google.gwt.http.client.URL;

import java.util.LinkedList;

public class Patch {
    public LinkedList<Diff> diffs;
    public int start1;
    public int start2;
    public int length1;
    public int length2;

    public Patch() {
        this.diffs = new LinkedList<Diff>();
    }

    /**
     * Emmulate GNU diff's format. Header: @@ -382,8 +481,9 @@ Indicies are
     * printed as 1-based, not 0-based.
     *
     * @return The GNU diff string.
     */
    public String toString() {
        String coords1, coords2;
        if (this.length1 == 0) {
            coords1 = this.start1 + ",0";
        } else if (this.length1 == 1) {
            coords1 = Integer.toString(this.start1 + 1);
        } else {
            coords1 = (this.start1 + 1) + "," + this.length1;
        }
        if (this.length2 == 0) {
            coords2 = this.start2 + ",0";
        } else if (this.length2 == 1) {
            coords2 = Integer.toString(this.start2 + 1);
        } else {
            coords2 = (this.start2 + 1) + "," + this.length2;
        }
        StringBuilder text = new StringBuilder();
        text.append("@@ -").append(coords1).append(" +").append(coords2).append(" @@\n");
        // Escape the body of the patch with %xx notation.
        for (Diff aDiff : this.diffs) {
            switch (aDiff.operation) {
                case INSERT:
                    text.append('+');
                    break;
                case DELETE:
                    text.append('-');
                    break;
                case EQUAL:
                    text.append(' ');
                    break;
            }
            text.append(URL.encode(aDiff.text).replace('+', ' ')).append("\n");
        }
        return unescapeForEncodeUriCompatability(text.toString());
    }

    public static String unescapeForEncodeUriCompatability(String str) {
        return str.replace("%21", "!").replace("%7E", "~").replace("%27", "'").replace("%28", "(").replace("%29", ")")
                .replace("%3B", ";").replace("%2F", "/").replace("%3F", "?").replace("%3A", ":").replace("%40", "@")
                .replace("%26", "&").replace("%3D", "=").replace("%2B", "+").replace("%24", "$").replace("%2C", ",")
                .replace("%23", "#");
    }
}

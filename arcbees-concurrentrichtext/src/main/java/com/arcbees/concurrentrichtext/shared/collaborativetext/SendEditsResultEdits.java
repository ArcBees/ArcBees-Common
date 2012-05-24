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

package com.arcbees.concurrentrichtext.shared.collaborativetext;

import com.arcbees.concurrentrichtext.shared.diffsync.Edits;

import java.util.List;

public class SendEditsResultEdits implements SendEditsResult {

    private static final long serialVersionUID = -5269978730082053208L;
    private List<Edits> editsList;

    @SuppressWarnings("unused")
    private SendEditsResultEdits() {
    }

    public SendEditsResultEdits(List<Edits> editsList) {
        this.editsList = editsList;
    }

    public List<Edits> getEdits() {
        return editsList;
    }

    @Override
    public ResultType getResultType() {
        return ResultType.EDITS;
    }

}

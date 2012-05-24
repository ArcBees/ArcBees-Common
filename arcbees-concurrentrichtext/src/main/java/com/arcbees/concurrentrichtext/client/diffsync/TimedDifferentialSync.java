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

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;

import com.arcbees.concurrentrichtext.client.util.ClientLogger;
import com.arcbees.concurrentrichtext.shared.AdaptativeTimer;
import com.arcbees.concurrentrichtext.shared.diffsync.DiffHandler;
import com.arcbees.concurrentrichtext.shared.diffsync.DocumentShadow;

public class TimedDifferentialSync extends AbstractDifferentialSync {
    private AdaptativeTimer timer;

    @Inject
    public TimedDifferentialSync(final DiffHandler diffHandler, final DocumentShadow docShadow,
            final ClientLogger logger, final @Named("EditsTimerMin") int timerMin,
            @Named("EditsTimerMax") final int timerMax, @Assisted final DifferentialSyncCallback callback) {
        super(diffHandler, docShadow, logger, callback);

        timer = new AdaptativeTimer(timerMin, timerMax) {
            @Override
            public void timerTick() {
                sendEdits();
            }
        };

        timer.start();
    }

    @Override
    protected void editsAdded() {
    }

    private void sendEdits() {
        synchronize();
    }
}

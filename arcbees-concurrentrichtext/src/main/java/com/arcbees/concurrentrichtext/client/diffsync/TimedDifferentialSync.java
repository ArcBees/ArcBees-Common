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
    public TimedDifferentialSync(DiffHandler diffHandler,
                                 DocumentShadow docShadow, ClientLogger logger,
                                 @Named("EditsTimerMin") int timerMin,
                                 @Named("EditsTimerMax") int timerMax,
                                 @Assisted DifferentialSyncCallback callback) {
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

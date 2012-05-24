package com.arcbees.concurrentrichtext.client.diffsync;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import com.arcbees.concurrentrichtext.client.util.ClientLogger;
import com.arcbees.concurrentrichtext.shared.diffsync.DiffHandler;
import com.arcbees.concurrentrichtext.shared.diffsync.DocumentShadow;

public class SimpleDifferentialSync extends AbstractDifferentialSync {

    @Inject
    public SimpleDifferentialSync(DiffHandler diffHandler,
                                  DocumentShadow docShadow, ClientLogger logger,
                                  @Assisted DifferentialSyncCallback callback) {
        super(diffHandler, docShadow, logger, callback);
    }

    @Override
    protected void editsAdded() {
        synchronize();
    }

}

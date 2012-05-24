package com.arcbees.concurrentrichtext.client.diffsync;

public interface DifferentialSyncFactory {
    DifferentialSync create(DifferentialSyncCallback callback);
}

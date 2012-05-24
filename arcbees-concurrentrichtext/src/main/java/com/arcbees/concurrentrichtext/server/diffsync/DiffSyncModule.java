package com.arcbees.concurrentrichtext.server.diffsync;

import com.arcbees.concurrentrichtext.shared.diffsync.DiffMatchPatch;
import com.arcbees.concurrentrichtext.shared.diffsync.DocumentShadow;
import com.google.inject.Module;
import com.gwtplatform.dispatch.server.guice.HandlerModule;

public class DiffSyncModule extends HandlerModule implements Module {

    @Override
    protected void configureHandlers() {
        bind(DiffMatchPatch.class).to(ServerDiffMatchPatch.class);
        bind(DifferentialSync.class).to(DifferentialSyncImpl.class);
        bind(DocumentShadow.class).to(ServerDocumentShadowImpl.class);
    }

}

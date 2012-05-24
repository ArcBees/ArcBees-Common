package com.arcbees.concurrentrichtext.server.diffsync;

import com.arcbees.concurrentrichtext.shared.diffsync.ApplyEditsResult;
import com.arcbees.concurrentrichtext.shared.diffsync.DocumentShadow;
import com.arcbees.concurrentrichtext.shared.diffsync.Edits;

public interface DifferentialSync {

    ApplyEditsResult applyEdits(Edits edits);

    Edits getEdits(String serverText);

    DocumentShadow getDocumentShadow();

    void initialize(String id, String baseText);

    void restoreFromBackup();

    boolean checkVersion(Edits edits);

    void leave();

}

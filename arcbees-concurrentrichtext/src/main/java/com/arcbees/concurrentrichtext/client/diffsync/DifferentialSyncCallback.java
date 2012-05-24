package com.arcbees.concurrentrichtext.client.diffsync;

import com.arcbees.concurrentrichtext.shared.diffsync.Edits;

import java.util.List;

public interface DifferentialSyncCallback {

    /**
     * This method is called when the DifferentialSync implementation wish to send
     * it's edits to the server
     *
     * @param editsList The list of edits to send
     */
    void onSynchronize(List<Edits> editsList);

}

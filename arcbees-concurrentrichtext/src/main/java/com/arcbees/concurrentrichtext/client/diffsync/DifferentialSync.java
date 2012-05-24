package com.arcbees.concurrentrichtext.client.diffsync;

import com.arcbees.concurrentrichtext.client.collaborativetext.CursorOffset;
import com.arcbees.concurrentrichtext.shared.diffsync.Edits;

import java.util.List;

public interface DifferentialSync {
    void onTextChange(String newText);

    ApplyEditsResultOffset onEditsReceived(List<Edits> editsList, CursorOffset cursor);

    void restore(String text, int clientVersion, int serverVersion);

    String getText();
}

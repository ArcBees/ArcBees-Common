package com.arcbees.addthis.client.widget;

import com.google.inject.assistedinject.Assisted;

public interface AddThisFactory {
    AddThisShare createShare(String url);

    AddThisShare createShare(@Assisted("url") String url,
                             @Assisted("title") String title,
                             @Assisted("description") String description);
}

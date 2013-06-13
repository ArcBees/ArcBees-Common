package com.arcbees.addthis.client.widget;

import com.arcbees.addthis.client.api.AddThisApi;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

public class AddThisShare extends Composite {
    public interface Binder extends UiBinder<Widget, AddThisShare> {
    }

    private static int num = 0;

    @UiField
    AnchorElement counter;
    @UiField
    DivElement toolbox;

    private final AddThisApi addThisApi;
    private final String url;

    @AssistedInject
    AddThisShare(Binder uiBinder,
                 AddThisApi addThisApi,
                 @Assisted String url) {
        this.url = url;
        this.addThisApi = addThisApi;
        initWidget(uiBinder.createAndBindUi(this));

        counter.setId("addthis_toolbox" + num);
        toolbox.setId("addthis_counter" + num);
        num++;

        toolbox.setAttribute("addthis:url", url);
    }

    @AssistedInject
    AddThisShare(Binder uiBinder,
                 AddThisApi addThisApi,
                 @Assisted("url") String url,
                 @Assisted("title") String title,
                 @Assisted("description") String description) {
        this(uiBinder, addThisApi, url);

        toolbox.setAttribute("addthis:title", title);
        toolbox.setAttribute("addthis:description", description);
    }

    @Override
    protected void onLoad() {
        if (url.isEmpty()) {
            addThisApi.renderToolbox("#" + toolbox.getId());
            addThisApi.renderCounter("#" + counter.getId());
        } else {
            addThisApi.renderToolbox("#" + toolbox.getId(), url);
            addThisApi.renderCounter("#" + counter.getId(), url);
        }
    }
}

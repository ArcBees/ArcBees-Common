package com.arcbees.website.assets.client.application.forkOnGithub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ForkOnGithubWidget extends Composite {
    interface ForkOnGithubWidgetUiBinder extends UiBinder<Widget, ForkOnGithubWidget> {
    }

    private static ForkOnGithubWidgetUiBinder ourUiBinder = GWT.create(ForkOnGithubWidgetUiBinder.class);

    @UiField
    AnchorElement link;

    public ForkOnGithubWidget() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    public void setLinkHref(String href) {
        link.setHref(href);
    }
}

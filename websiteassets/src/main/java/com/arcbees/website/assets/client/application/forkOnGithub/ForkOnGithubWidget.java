package com.arcbees.website.assets.client.application.forkOnGithub;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ForkOnGithubWidget extends Composite {
    interface ForkOnGithubWidgetUiBinder extends UiBinder<Widget, ForkOnGithubWidget> {
    }

    private static ForkOnGithubWidgetUiBinder ourUiBinder = GWT.create(ForkOnGithubWidgetUiBinder.class);

    public ForkOnGithubWidget() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}

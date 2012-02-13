package com.arcbees.facebook.sample.client.application;

import com.arcbees.facebook.client.Callback;
import com.arcbees.facebook.client.Facebook;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class ApplicationPresenter extends Presenter<ApplicationPresenter.MyView, ApplicationPresenter.MyProxy> {
    private final Facebook facebook;

    public interface MyView extends View {
    }

    @ProxyStandard
    @NameToken("home")
    public interface MyProxy extends ProxyPlace<ApplicationPresenter> {
    }

    @Inject
    public ApplicationPresenter(final EventBus eventBus, final MyView view, final MyProxy proxy,
                                final Facebook facebook) {
        super(eventBus, view, proxy);

        this.facebook = facebook;
    }

    @Override
    protected void onBind() {
        super.onBind();

        facebook.injectFacebookApi(new Callback() {
            @Override
            public void onSuccess() {
            }
        });
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(this, this);
    }
}

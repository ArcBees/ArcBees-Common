package com.arcbees.facebook.sample.client.gin;

import com.arcbees.facebook.sample.client.application.ApplicationPresenter;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;

@GinModules({ClientModule.class})
public interface ClientGinjector extends Ginjector {
    EventBus getEventBus();

    PlaceManager getPlaceManager();

    Provider<ApplicationPresenter> getApplicationPresenter();
}

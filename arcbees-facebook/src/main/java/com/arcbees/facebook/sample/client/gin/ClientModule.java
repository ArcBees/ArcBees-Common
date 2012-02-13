package com.arcbees.facebook.sample.client.gin;

import com.arcbees.facebook.client.gin.FacebookModule;
import com.arcbees.facebook.sample.client.application.ApplicationModule;
import com.arcbees.facebook.sample.client.place.ClientPlaceManager;
import com.google.gwt.inject.client.AbstractGinModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

public class ClientModule extends AbstractGinModule {
    @Override
    protected void configure() {
        install(new DefaultModule(ClientPlaceManager.class));
        install(new FacebookModule());

        install(new ApplicationModule());
    }
}

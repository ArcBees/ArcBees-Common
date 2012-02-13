package com.arcbees.facebook.client.gin;

import com.arcbees.facebook.client.Api;
import com.arcbees.facebook.client.Facebook;
import com.google.gwt.inject.client.AbstractGinModule;

import javax.inject.Singleton;

public class FacebookModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(Facebook.class).in(Singleton.class);
        bind(Api.class).in(Singleton.class);
    }
}

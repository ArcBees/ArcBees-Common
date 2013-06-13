package com.arcbees.addthis.client.widget;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;

public class AddThisModule extends AbstractGinModule {
    @Override
    protected void configure() {
        install(new GinFactoryModuleBuilder().build(AddThisFactory.class));
    }
}

/*
 * Copyright 2011 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.concurrentrichtext.sample.client.gin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;

import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.name.Names;

import com.arcbees.concurrentrichtext.client.ConcurrentRichTextModule;
import com.arcbees.concurrentrichtext.client.diffsync.DifferentialSync;
import com.arcbees.concurrentrichtext.client.diffsync.DifferentialSyncFactory;
import com.arcbees.concurrentrichtext.client.diffsync.TimedDifferentialSync;
import com.arcbees.concurrentrichtext.sample.client.HomePresenter;
import com.arcbees.concurrentrichtext.sample.client.HomeView;
import com.arcbees.concurrentrichtext.sample.client.place.ClientPlaceManager;
import com.arcbees.concurrentrichtext.sample.client.place.DefaultPlace;
import com.arcbees.concurrentrichtext.sample.client.place.NameTokens;

public class ClientModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new DefaultModule(ClientPlaceManager.class));

        bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.home);
        bindPresenter(HomePresenter.class, HomePresenter.MyView.class, HomeView.class, HomePresenter.MyProxy.class);

        // Concurrent Rich Text Specifics
        install(new ConcurrentRichTextModule());
        install(new GinFactoryModuleBuilder().implement(DifferentialSync.class, TimedDifferentialSync.class)
                .build(DifferentialSyncFactory.class));
        bindConstant().annotatedWith(Names.named("DiffTimer")).to(500);
        bindConstant().annotatedWith(Names.named("EditsTimerMin")).to(2000);
        bindConstant().annotatedWith(Names.named("EditsTimerMax")).to(10000);
    }
}

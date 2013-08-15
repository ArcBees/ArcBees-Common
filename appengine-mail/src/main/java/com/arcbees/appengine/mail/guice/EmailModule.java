/**
 * Copyright 2013 ArcBees Inc.
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

package com.arcbees.appengine.mail.guice;

import com.arcbees.appengine.mail.EmailSender;
import com.arcbees.appengine.mail.EmailSenderImpl;
import com.arcbees.appengine.mail.TaskOptionsBuilder;
import com.arcbees.appengine.mail.TaskOptionsWithPayloadBuilder;
import com.arcbees.appengine.mail.Transport;
import com.arcbees.appengine.mail.TransportImpl;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.inject.AbstractModule;

public class EmailModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EmailSender.class).to(EmailSenderImpl.class);
        bind(Transport.class).to(TransportImpl.class);
        bind(TaskOptionsBuilder.class).to(TaskOptionsWithPayloadBuilder.class);
        bind(Queue.class).toInstance(QueueFactory.getDefaultQueue());
    }
}

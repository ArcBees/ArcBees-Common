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

package com.arcbees.appengine.mail;

import javax.inject.Inject;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class EmailSenderImpl implements EmailSender {
    public static EmailSender buildDefault() {
        return new EmailSenderImpl(QueueFactory.getDefaultQueue(), new TransportImpl(),
                new TaskOptionsWithPayloadBuilder());
    }

    private final Queue queue;
    private final Transport transport;
    private final TaskOptionsBuilder taskOptionsBuilder;

    @Inject
    EmailSenderImpl(Queue queue,
                    Transport transport,
                    TaskOptionsBuilder taskOptionsBuilder) {
        this.queue = queue;
        this.transport = transport;
        this.taskOptionsBuilder = taskOptionsBuilder;
    }

    @Override
    public void send(Email email) {
        DeferredTask task = new SendEmailTask(email, transport);

        TaskOptions taskOptions = taskOptionsBuilder.build(task);

        queue.add(taskOptions);
    }
}

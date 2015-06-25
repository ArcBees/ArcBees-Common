/*
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

import javax.mail.Message;
import javax.mail.MessagingException;

import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SendEmailTaskTest {
    @Test
    public void run_mockedTransport_transportSendAnyMessage() throws MessagingException {
        //given
        Email email = mock(Email.class);
        when(email.getFromAddress()).thenReturn("a@a.com");
        when(email.getFromPersonal()).thenReturn("A Personal");
        when(email.getTo()).thenReturn("b@b.com");
        when(email.getReplyToAddress()).thenReturn("c@c.com");
        Transport transport = mock(Transport.class);
        SendEmailTask sendEmailTask = new SendEmailTask(email, transport);

        //when
        sendEmailTask.run();

        //then
        verify(transport).send(any(Message.class));
    }
}

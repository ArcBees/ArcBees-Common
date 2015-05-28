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

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.appengine.api.taskqueue.DeferredTask;

public class SendEmailTask implements DeferredTask {
    private static final String CONTENT_TYPE = "text/html";
    private static Logger logger = Logger.getLogger(SendEmailTask.class.getName());

    private final Email email;
    private final Transport transport;

    SendEmailTask(Email email,
                  Transport transport) {
        this.email = email;
        this.transport = transport;
    }

    @Override
    public void run() {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        Message message = new MimeMessage(session);

        try {
            if (email.getFromPersonal().equals(EmailBuilder.DEFAULT_PERSONAL)) {
                message.setFrom(new InternetAddress(email.getFromAddress()));
            } else {
                message.setFrom(new InternetAddress(email.getFromAddress(), email.getFromPersonal()));
            }
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
            message.setSubject(email.getSubject());
            message.setContent(email.getBody(), CONTENT_TYPE);
            message.setReplyTo(new Address[] {
                    new InternetAddress(email.getReplyToAddress())
            });

            transport.send(message);
        } catch (MessagingException e) {
            logger.log(Level.WARNING, e.getMessage());
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
}

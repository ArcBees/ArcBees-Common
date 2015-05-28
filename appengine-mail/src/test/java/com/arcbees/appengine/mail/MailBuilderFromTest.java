/**
 * Copyright 2013 ArcBees Inc.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.appengine.mail;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MailBuilderFromTest {

    public static final String TO = "to";
    public static final String FROM_ADDRESS = "fromAddress";
    public static final String FROM_PERSONAL = "fromPersonal";
    public static final String BODY = "body";
    public static final String SUBJECT = "subject";
    public static final String REPLY_TO_ADDRESS = "replyToAddress";

    @Test
    public void build_allFieldsFilled_emailIsWellBuilt() {
        //given
        EmailBuilder.MailBuilderFromAddress mailBuilderFromAddress = EmailBuilder.to(TO).fromAddress(FROM_ADDRESS).fromPersonal
                (FROM_PERSONAL).body(BODY).subject(SUBJECT).replyToAddress(REPLY_TO_ADDRESS);

        //when
        Email email = mailBuilderFromAddress.build();

        //then
        assertEquals(TO, email.getTo());
        assertEquals(FROM_ADDRESS, email.getFromAddress());
        assertEquals(FROM_PERSONAL, email.getFromPersonal());
        assertEquals(BODY, email.getBody());
        assertEquals(SUBJECT, email.getSubject());
        assertEquals(REPLY_TO_ADDRESS, email.getReplyToAddress());
    }

    @Test
    public void build_missingBody_emailIsWellBuilt() {
        //given
        EmailBuilder.MailBuilderFromAddress mailBuilderFromAddress = EmailBuilder.to(TO).fromAddress(FROM_ADDRESS).fromPersonal
                (FROM_PERSONAL).subject(SUBJECT).replyToAddress(REPLY_TO_ADDRESS);

        //when
        Email email = mailBuilderFromAddress.build();

        //then
        assertEquals(TO, email.getTo());
        assertEquals(FROM_ADDRESS, email.getFromAddress());
        assertEquals(FROM_PERSONAL, email.getFromPersonal());
        assertEquals("", email.getBody());
        assertEquals(SUBJECT, email.getSubject());
        assertEquals(REPLY_TO_ADDRESS, email.getReplyToAddress());
    }

    @Test
    public void build_missingSubject_emailIsWellBuilt() {
        //given
        EmailBuilder.MailBuilderFromAddress mailBuilderFromAddress = EmailBuilder.to(TO).fromAddress(FROM_ADDRESS).fromPersonal
                (FROM_PERSONAL).body(BODY).replyToAddress(REPLY_TO_ADDRESS);

        //when
        Email email = mailBuilderFromAddress.build();

        //then
        assertEquals(TO, email.getTo());
        assertEquals(FROM_ADDRESS, email.getFromAddress());
        assertEquals(FROM_PERSONAL, email.getFromPersonal());
        assertEquals(BODY, email.getBody());
        assertEquals("", email.getSubject());
        assertEquals(REPLY_TO_ADDRESS, email.getReplyToAddress());
    }

    @Test
    public void build_missingPersonal_emailIsWellBuilt() {
        //given
        EmailBuilder.MailBuilderFromAddress mailBuilderFromAddress = EmailBuilder.to(TO).fromAddress(FROM_ADDRESS).body
                (BODY).subject(SUBJECT).replyToAddress(REPLY_TO_ADDRESS);

        //when
        Email email = mailBuilderFromAddress.build();

        //then
        assertEquals(TO, email.getTo());
        assertEquals(FROM_ADDRESS, email.getFromAddress());
        assertEquals(EmailBuilder.DEFAULT_PERSONAL, email.getFromPersonal());
        assertEquals(BODY, email.getBody());
        assertEquals(SUBJECT, email.getSubject());
        assertEquals(REPLY_TO_ADDRESS, email.getReplyToAddress());
    }

    @Test
    public void build_missingReplyToAddress_emailIsWellBuilt() {
        //given
        EmailBuilder.MailBuilderFromAddress mailBuilderFromAddress = EmailBuilder.to(TO).fromAddress(FROM_ADDRESS).fromPersonal
                (FROM_PERSONAL).body(BODY).subject(SUBJECT);

        //when
        Email email = mailBuilderFromAddress.build();

        //then
        assertEquals(TO, email.getTo());
        assertEquals(FROM_ADDRESS, email.getFromAddress());
        assertEquals(FROM_PERSONAL, email.getFromPersonal());
        assertEquals(BODY, email.getBody());
        assertEquals(SUBJECT, email.getSubject());
        assertEquals("", email.getReplyToAddress());
    }
}

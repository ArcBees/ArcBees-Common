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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MailBuilderFromTest {
    @Test
    public void build_allFieldsFilled_emailIsWellBuilt() {
        //given
        EmailBuilder.MailBuilderFromAddress mailBuilderFromAddress = EmailBuilder.to("to").fromAddress("fromAddress").fromPersonal
                ("fromPersonal").body("body").subject("subject");

        //when
        Email email = mailBuilderFromAddress.build();

        //then
        assertEquals("to", email.getTo());
        assertEquals("fromAddress", email.getFromAddress());
        assertEquals("fromPersonal", email.getFromPersonal());
        assertEquals("body", email.getBody());
        assertEquals("subject", email.getSubject());
    }

    @Test
    public void build_missingBody_emailIsWellBuilt() {
        //given
        EmailBuilder.MailBuilderFromAddress mailBuilderFromAddress = EmailBuilder.to("to").fromAddress("fromAddress").fromPersonal
                ("fromPersonal").subject("subject");

        //when
        Email email = mailBuilderFromAddress.build();

        //then
        assertEquals("to", email.getTo());
        assertEquals("fromAddress", email.getFromAddress());
        assertEquals("fromPersonal", email.getFromPersonal());
        assertEquals("", email.getBody());
        assertEquals("subject", email.getSubject());
    }

    @Test
    public void build_missingSubject_emailIsWellBuilt() {
        //given
        EmailBuilder.MailBuilderFromAddress mailBuilderFromAddress = EmailBuilder.to("to").fromAddress("fromAddress").fromPersonal
                ("fromPersonal").body("body");

        //when
        Email email = mailBuilderFromAddress.build();

        //then
        assertEquals("to", email.getTo());
        assertEquals("fromAddress", email.getFromAddress());
        assertEquals("fromPersonal", email.getFromPersonal());
        assertEquals("body", email.getBody());
        assertEquals("", email.getSubject());
    }

    @Test
    public void build_missingPersonal_emailIsWellBuilt() {
        //given
        EmailBuilder.MailBuilderFromAddress mailBuilderFromAddress = EmailBuilder.to("to").fromAddress("fromAddress").body
                ("body").subject("subject");

        //when
        Email email = mailBuilderFromAddress.build();

        //then
        assertEquals("to", email.getTo());
        assertEquals("fromAddress", email.getFromAddress());
        assertEquals("fromAddress", email.getFromPersonal());
        assertEquals("body", email.getBody());
        assertEquals("subject", email.getSubject());
    }
}

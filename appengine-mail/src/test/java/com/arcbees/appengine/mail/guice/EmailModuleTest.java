package com.arcbees.appengine.mail.guice;

import org.junit.Test;

import com.arcbees.appengine.mail.EmailSender;
import com.arcbees.appengine.mail.EmailSenderImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;

import static org.junit.Assert.assertTrue;

public class EmailModuleTest {
    @Test
    public void installModule_defaultModule_emailSenderIsBound() {
        //given
        Injector injector = Guice.createInjector(new EmailModule());

        //when
        EmailSender emailSender = injector.getInstance(EmailSender.class);

        //then
        assertTrue(emailSender instanceof EmailSenderImpl);
    }
}

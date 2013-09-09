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
        when(email.getTo()).thenReturn("b@b.com");
        Transport transport = mock(Transport.class);
        SendEmailTask sendEmailTask = new SendEmailTask(email, transport);

        //when
        sendEmailTask.run();

        //then
        verify(transport).send(any(Message.class));
    }
}

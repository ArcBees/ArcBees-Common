package com.arcbees.appengine.mail;

import javax.mail.Message;
import javax.mail.MessagingException;

public class TransportImpl implements Transport {
    @Override
    public void send(Message message) throws MessagingException {
        javax.mail.Transport.send(message);
    }
}

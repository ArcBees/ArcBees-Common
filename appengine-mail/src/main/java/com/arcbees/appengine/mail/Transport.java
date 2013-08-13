package com.arcbees.appengine.mail;

import javax.mail.Message;
import javax.mail.MessagingException;

public interface Transport {
    void send(Message message) throws MessagingException;
}

package com.arcbees.appengine.mail;

import java.io.Serializable;

import javax.mail.Message;
import javax.mail.MessagingException;

public interface Transport extends Serializable {
    void send(Message message) throws MessagingException;
}

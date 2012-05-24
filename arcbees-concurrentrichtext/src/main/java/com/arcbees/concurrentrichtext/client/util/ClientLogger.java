package com.arcbees.concurrentrichtext.client.util;

import com.gwtplatform.dispatch.shared.DispatchAsync;

import com.google.inject.Inject;

import com.arcbees.concurrentrichtext.client.dispatch.AsyncCallbackImpl;
import com.arcbees.concurrentrichtext.shared.LoggingMessage;
import com.arcbees.concurrentrichtext.shared.LoggingMessage.LogLevel;
import com.arcbees.concurrentrichtext.shared.NoResult;

public class ClientLogger {

    private final DispatchAsync dispatcher;

    @Inject
    public ClientLogger(DispatchAsync dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void log(LogLevel level, String message) {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        String method = stack.length >= 1 ? stack[1].toString() : "";
        LoggingMessage logMsg = new LoggingMessage(level, method + " " + message);
        dispatcher.execute(logMsg, new AsyncCallbackImpl<NoResult>() {
            @Override
            public void onSuccess(NoResult result) {
            }
        });
    }
}

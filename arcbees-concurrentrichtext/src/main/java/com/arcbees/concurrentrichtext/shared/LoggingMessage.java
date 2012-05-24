package com.arcbees.concurrentrichtext.shared;

import com.gwtplatform.dispatch.shared.ActionImpl;

public class LoggingMessage extends ActionImpl<NoResult> {

    public enum LogLevel {
        INFO, WARNING, SEVERE
    }

    private LogLevel level;
    private String message;

    LoggingMessage() {
    }

    public LoggingMessage(LogLevel level, String message) {
        this.level = level;
        this.message = message;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean isSecured() {
        return false;
    }
}

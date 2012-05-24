package com.arcbees.concurrentrichtext.server;

import com.arcbees.concurrentrichtext.shared.LoggingMessage;
import com.arcbees.concurrentrichtext.shared.LoggingMessage.LogLevel;
import com.arcbees.concurrentrichtext.shared.NoResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingHandler extends
                            AbstractActionHandler<LoggingMessage, NoResult> {

    private final Logger logger;

    @Inject
    public LoggingHandler(final Logger logger) {
        super(LoggingMessage.class);
        this.logger = logger;
    }

    @Override
    public NoResult execute(LoggingMessage action, ExecutionContext context)
            throws ActionException {
        Level level = getLevel(action.getLevel());
        logger.log(level, action.getMessage());
        return new NoResult();
    }

    private Level getLevel(LogLevel logLevel) {
        Level level = Level.OFF;
        switch (logLevel) {
            case INFO:
                level = Level.INFO;
                break;
            case SEVERE:
                level = Level.SEVERE;
                break;
            case WARNING:
                level = Level.WARNING;
                break;
        }
        return level;
    }

    @Override
    public void undo(LoggingMessage action, NoResult result,
                     ExecutionContext context) throws ActionException {
    }
}

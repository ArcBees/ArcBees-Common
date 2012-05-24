/*
 * Copyright 2011 ArcBees Inc.
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

package com.arcbees.concurrentrichtext.server;

import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.server.actionhandler.AbstractActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import com.google.inject.Inject;

import com.arcbees.concurrentrichtext.shared.LoggingMessage;
import com.arcbees.concurrentrichtext.shared.LoggingMessage.LogLevel;
import com.arcbees.concurrentrichtext.shared.NoResult;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingHandler extends AbstractActionHandler<LoggingMessage, NoResult> {
    private final Logger logger;

    @Inject
    public LoggingHandler(final Logger logger) {
        super(LoggingMessage.class);

        this.logger = logger;
    }

    @Override
    public NoResult execute(LoggingMessage action, ExecutionContext context) throws ActionException {
        Level level = getLevel(action.getLevel());
        logger.log(level, action.getMessage());
        return new NoResult();
    }

    @Override
    public void undo(LoggingMessage action, NoResult result, ExecutionContext context) throws ActionException {
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
}

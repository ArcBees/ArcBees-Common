package com.arcbees.appengine.mail;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.TaskOptions;

public interface TaskOptionsBuilder {
    TaskOptions build(DeferredTask defferedTask);
}

package com.arcbees.appengine.mail;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.TaskOptions;

public class TaskOptionsWithPayloadBuilder implements TaskOptionsBuilder {
    @Override
    public TaskOptions build(DeferredTask defferedTask) {
        return TaskOptions.Builder.withPayload(defferedTask);
    }
}

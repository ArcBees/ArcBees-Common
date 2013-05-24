package com.arcbees.test;

import java.io.IOException;

import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

import cucumber.api.junit.Cucumber;
import cucumber.runtime.junit.FeatureRunner;

public class CucumberRunner extends Cucumber {
    private final RunListener listener = new RunListener() {
        @Override
        public void testStarted(Description description) throws Exception {
            sleepForDemo();
        }
    };

    private final Class clazz;

    public CucumberRunner(Class clazz) throws InitializationError, IOException {
        super(clazz);

        this.clazz = clazz;
    }

    @Override
    public void run(RunNotifier notifier) {
        notifier.addListener(listener);

        super.run(notifier);

        notifier.removeListener(listener);
    }

    @Override
    protected Description describeChild(FeatureRunner child) {
        Description description = super.describeChild(child);
        description.setfTestClass(clazz);
        return description;
    }

    protected void sleepForDemo() {
        if (TestParameters.IS_DEMO) {
            sleep(TestParameters.DEMO_SLEEP_TIME);
        }
    }

    protected void sleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
        }
    }
}

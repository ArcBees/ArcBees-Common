package com.arcbees.test;

import com.google.common.base.Strings;

public interface TestParameters {
    boolean IS_DEMO = !Strings.isNullOrEmpty(System.getProperty("demoMode"))
            && "true".equals(System.getProperty("demoMode").toLowerCase());
    long DEMO_SLEEP_TIME = Strings.isNullOrEmpty(System.getProperty("demoDelay"))
            ? 1000L : Long.valueOf(System.getProperty("demoDelay"));
    boolean USE_SELENIUM_GRID = !Strings.isNullOrEmpty(System.getProperty("useSeleniumGrid"))
            && "true".equals(System.getProperty("useSeleniumGrid").toLowerCase());
    String SELENIUM_HUB_URL = System.getProperty("seleniumUrl");
    String BROWSER = System.getProperty("browser");
}

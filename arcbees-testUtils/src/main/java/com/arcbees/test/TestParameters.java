/*
 * Copyright 2013 ArcBees Inc.
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

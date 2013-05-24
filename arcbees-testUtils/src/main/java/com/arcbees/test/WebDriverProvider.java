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

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.arcbees.test.annotation.ImplicitWait;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class WebDriverProvider implements Provider<WebDriver> {
    private final Long implicitWait;
    private WebDriver webDriver;

    @Inject
    WebDriverProvider(ParameterHolder parameterHolder) {
        this.implicitWait = parameterHolder.implicityWait;
    }

    @Override
    public WebDriver get() {
        return getDefaultWebDriver();
    }

    private WebDriver getDefaultWebDriver() {
        if (webDriver == null) {
            webDriver = new ChromeDriver();
            webDriver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.MILLISECONDS);
        }
        return webDriver;
    }

    static class ParameterHolder {
        @Inject(optional = true)
        @ImplicitWait
        Long implicityWait = 0L;
    }
}

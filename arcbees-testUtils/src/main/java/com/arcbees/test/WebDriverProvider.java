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

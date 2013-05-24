package com.arcbees.test;

import javax.inject.Singleton;

import org.openqa.selenium.WebDriver;

import com.google.inject.AbstractModule;

import static org.aspectj.lang.Aspects.aspectOf;

public class CucumberModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(org.openqa.selenium.support.pagefactory.ElementLocatorFactory.class)
                .to(ElementLocatorFactory.class)
                .in(Singleton.class);

        bind(WebDriver.class).toProvider(WebDriverProvider.class);
        bind(WebDriverProvider.class).in(Singleton.class);
        requestInjection(aspectOf(CurrentScenarioAspect.class));
    }
}

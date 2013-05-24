package com.arcbees.test;

import java.lang.reflect.Field;

import javax.inject.Inject;

import org.openqa.selenium.WebDriver;

public class ElementLocatorFactory implements org.openqa.selenium.support.pagefactory.ElementLocatorFactory {
    private final WebDriver driver;

    @Inject
    public ElementLocatorFactory(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public org.openqa.selenium.support.pagefactory.ElementLocator createLocator(Field field) {
        return new ElementLocator(driver, field);
    }
}

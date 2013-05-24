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

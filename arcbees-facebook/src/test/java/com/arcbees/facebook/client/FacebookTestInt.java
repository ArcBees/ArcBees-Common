package com.arcbees.facebook.client;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.Nullable;

public class FacebookTestInt {
    private static String LOCALHOST = "http://localhost:8080/";
    private static String FACEBOOK_APP_ID = "233908489992846";
    private static WebDriver webDriver;

    @BeforeClass
    public static void setUpClass() {
        webDriver = new FirefoxDriver();
    }

    @Test
    public void facebookInjectionTest() {
        // given
        WebDriverWait wait = new WebDriverWait(webDriver, 20);
        webDriver.get(LOCALHOST);

        //when
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(@Nullable WebDriver input) {
                return null != input.findElement(By.id("fb-root"));
            }
        });
    }

    @Test
    public void facebookLoginTest() {
    }

    @AfterClass
    public static void tearDownClass() {
        webDriver.quit();
    }
}

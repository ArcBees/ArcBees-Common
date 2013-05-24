package com.arcbees.test;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.arcbees.test.annotation.ImplicitWait;
import com.google.inject.Inject;

import cucumber.runtime.StepDefinitionMatch;
import cucumber.runtime.model.CucumberScenario;
import gherkin.formatter.model.Step;

import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;

@Aspect
public class CurrentScenarioAspect {
    private static final ThreadLocal<CucumberScenario> scenario = new ThreadLocal<CucumberScenario>();
    private static final ConcurrentMap<String, WebDriver> webDrivers = new ConcurrentHashMap<String, WebDriver>();
    private static final ConcurrentMap<Step, CucumberScenario> steps = new ConcurrentHashMap<Step, CucumberScenario>();
    private static final ReentrantLock lock = new ReentrantLock(true);

    @Inject(optional = true)
    @ImplicitWait
    private Long implicitWait = 0L;

    @Before("(call(public * cucumber.runtime.model.CucumberScenario.run(..))) " +
            "|| (call(public * cucumber.runtime.model.CucumberTagStatement.run(..)) " +
            "&& target(cucumber.runtime.model.CucumberScenario))")
    public void beforeRunningScenario(JoinPoint thisJoinPoint) throws InterruptedException {
        scenario.set((CucumberScenario) thisJoinPoint.getTarget());
        lock.lock();
        registerSteps(scenario.get());
    }

    @After("call(public * cucumber.runtime.model.CucumberScenario.run(..)) " +
            "|| (call(public * cucumber.runtime.model.CucumberTagStatement.run(..))" +
            "&& target(cucumber.runtime.model.CucumberScenario))")
    public void afterRunningScenario() {
        webDrivers.remove(getScenarioName());
        lock.unlock();
        scenario.set(null);
    }

    @Before("call(public * cucumber.runtime.StepDefinitionMatch.runStep(..)) && within(cucumber.runtime.Runtime)")
    public void beforeRunningStep(JoinPoint thisJoinPoint) {
        StepDefinitionMatch stepDefinitionMatch = (StepDefinitionMatch) thisJoinPoint.getTarget();
        Step step = (Step) getFieldValueInObject(stepDefinitionMatch, "step");
        setScenarioFromStep(step);
    }

    @After("call(public * cucumber.runtime.StepDefinitionMatch.runStep(..)) && within(cucumber.runtime.Runtime)")
    public void afterRunningStep(JoinPoint thisJoinPoint) {
        StepDefinitionMatch stepDefinitionMatch = (StepDefinitionMatch) thisJoinPoint.getTarget();
        Step step = (Step) getFieldValueInObject(stepDefinitionMatch, "step");
        steps.remove(step);
    }

    @Around("call(public * javax.inject.Provider.get(..)) && target(com.arcbees.test.WebDriverProvider)")
    public java.lang.Object aroundGetWebDriver(ProceedingJoinPoint thisJoinPoint) throws Throwable {
        if (TestParameters.USE_SELENIUM_GRID) {
            return getGridWebDriver();
        } else {
            return getDefaultWebDriver();
        }
    }

    private void registerSteps(CucumberScenario scenario) {
        registerSteps(scenario, scenario.getSteps());
        if (scenario.getCucumberBackground() != null) {
            registerSteps(scenario, scenario.getCucumberBackground().getSteps());
        }
    }

    private void registerSteps(CucumberScenario scenario, List<Step> scenarioSteps) {
        for (Step step : scenarioSteps) {
            steps.put(step, scenario);
        }
    }

    private void setScenarioFromStep(Step step) {
        CucumberScenario stepScenario = steps.get(step);
        scenario.set(stepScenario);
    }

    private WebDriver getDefaultWebDriver() {
        WebDriver webDriver = webDrivers.get(getScenarioName());
        if (webDriver == null) {
            webDriver = new ChromeDriver();
            initWebDriver(webDriver);
        }
        return webDriver;
    }

    private WebDriver getGridWebDriver() throws MalformedURLException {
        WebDriver webDriver = webDrivers.get(getScenarioName());
        if (webDriver == null) {
            webDriver = createGridWebDriver();
            initWebDriver(webDriver);
        }
        return webDriver;
    }

    private void initWebDriver(WebDriver webDriver) {
        if (implicitWait > 0) {
            webDriver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.MILLISECONDS);
        }
        webDrivers.put(getScenarioName(), webDriver);
    }

    private WebDriver createGridWebDriver() throws MalformedURLException {
        URL hubUrl = new URL(TestParameters.SELENIUM_HUB_URL);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(BROWSER_NAME, TestParameters.BROWSER);
        return new RemoteWebDriver(hubUrl, desiredCapabilities);
    }

    private String getScenarioName() {
        return scenario.get().getVisualName();
    }

    private <T> Object getFieldValueInObject(T m, String field) {
        try {
            Field fieldInObject = getFieldInObject(m, field);
            fieldInObject.setAccessible(true);
            return fieldInObject.get(m);
        } catch (Exception e) {
            return null;
        }
    }

    private <T> Field getFieldInObject(T m, String field) throws NoSuchFieldException {
        return m.getClass().getDeclaredField(field);
    }
}

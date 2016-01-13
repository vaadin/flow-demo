package com.vaadin.hummingbird;

import org.junit.Before;
import org.junit.Rule;

import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;

public class AbstractTestBenchTest extends TestBenchTestCase {

    @Rule
    public ScreenshotOnFailureRule screenshotOnFailure = new ScreenshotOnFailureRule(
            this, true);

    @Before
    public void setupDriver() throws Exception {

        String deploymentUrl = System
                .getProperty("com.vaadin.testbench.deployment.url");
        // deploymentUrl = "http://192.168.2.161:8888/";

        if (deploymentUrl != null && !deploymentUrl.isEmpty()) {
            String hubUrl = "http://tb3-hub.intra.itmill.com:4444/wd/hub";

            DesiredCapabilities capabilities = new DesiredCapabilities(
                    BrowserType.PHANTOMJS, "2", Platform.LINUX);
            capabilities.setCapability("phantomjs.binary.path",
                    "/usr/bin/phantomjs2");

            // com.vaadin.testbench.deployment.url is based on framework dev
            // server settings, use jetty:run port instead
            deploymentUrl = deploymentUrl.replaceAll(":8888", ":8080");

            setDriver(new RemoteWebDriver(new URL(hubUrl), capabilities));
            baseUrl = deploymentUrl + "?restartApplication";

        } else {
            setDriver(new PhantomJSDriver());
            baseUrl = "http://localhost:8080/?restartApplication";
        }
    }

    private String baseUrl;

    protected void openUrl(String... parameters) {
        String url = baseUrl;
        if (parameters != null && parameters.length != 0) {
            url += "&" + Arrays.stream(parameters)
                    .collect(Collectors.joining("&"));
        }
        getDriver().get(url);
    }

    /**
     * Waits up to 10s for the given condition to become true. Use e.g. as
     * {@link #waitUntil(ExpectedConditions.textToBePresentInElement(by, text))}
     *
     * @param condition
     *            the condition to wait for to become true
     */
    protected <T> void waitUntil(ExpectedCondition<T> condition) {
        waitUntil(condition, 10);
    }

    /**
     * Waits the given number of seconds for the given condition to become true.
     * Use e.g. as
     * {@link #waitUntil(ExpectedConditions.textToBePresentInElement(by, text))}
     *
     * @param condition
     *            the condition to wait for to become true
     */
    protected <T> void waitUntil(ExpectedCondition<T> condition,
            long timeoutInSeconds) {
        new WebDriverWait(driver, timeoutInSeconds).until(condition);
    }

    /**
     * Waits up to 10s for the given condition to become false. Use e.g. as
     * {@link #waitUntilNot(ExpectedConditions.textToBePresentInElement(by,
     * text))}
     *
     * @param condition
     *            the condition to wait for to become false
     */
    protected <T> void waitUntilNot(ExpectedCondition<T> condition) {
        waitUntilNot(condition, 10);
    }

    /**
     * Waits the given number of seconds for the given condition to become
     * false. Use e.g. as
     * {@link #waitUntilNot(ExpectedConditions.textToBePresentInElement(by,
     * text))}
     *
     * @param condition
     *            the condition to wait for to become false
     */
    protected <T> void waitUntilNot(ExpectedCondition<T> condition,
            long timeoutInSeconds) {
        waitUntil(ExpectedConditions.not(condition), timeoutInSeconds);
    }

    protected void waitForElementPresent(final By by) {
        waitUntil(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected void waitForElementNotPresent(final By by) {
        waitUntil(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return input.findElements(by).isEmpty();
            }
        });
    }

    protected void waitForElementVisible(final By by) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Checks if the given element has the given class name.
     *
     * Matches only full class names, i.e. has ("foo") does not match
     * class="foobar"
     *
     * @param element
     * @param className
     * @return
     */
    protected boolean hasCssClass(WebElement element, String className) {
        String classes = element.getAttribute("class");
        if (classes == null || classes.isEmpty()) {
            return (className == null || className.isEmpty());
        }

        for (String cls : classes.split(" ")) {
            if (className.equals(cls)) {
                return true;
            }
        }

        return false;
    }
}

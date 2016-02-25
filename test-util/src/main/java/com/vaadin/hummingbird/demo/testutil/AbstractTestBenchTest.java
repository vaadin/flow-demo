package com.vaadin.hummingbird.demo.testutil;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;

/**
 * Base class for demo tests, providing setup and helper methods..
 *
 * @author Vaadin
 * @since
 */
public class AbstractTestBenchTest extends TestBenchTestCase {

    /**
     * Grab a screenshot if the test fails. The rule handles terminating the
     * driver regardless of if the test fails or succeeds.
     */
    @Rule
    public ScreenshotOnFailureRule screenshotOnFailure = new ScreenshotOnFailureRule(
            this, true);

    private String baseUrl;

    /**
     * Creates the webdriver used for tests.
     *
     * @throws Exception
     *             if anything at all goes wrong
     */
    @Before
    public void setupDriver() throws Exception {
        setDriver(new PhantomJSDriver());
        baseUrl = "http://localhost:8080/?restartApplication";
    }

    /**
     * Opens the test url using the given, optional query parameters.
     *
     * @param parameters
     *            the parameters to append to the url, each in the format "foo"
     *            or "foo=bar"
     */
    protected void open(String... parameters) {
        String url = baseUrl;
        if (parameters != null && parameters.length != 0) {
            url += "&" + Arrays.stream(parameters)
                    .collect(Collectors.joining("&"));
        }
        getDriver().get(url);
    }

    /**
     * Waits up to 10s for the given condition to become true. Use e.g. as
     * {@code waitUntil(ExpectedConditions.textToBePresentInElement(by, text))}
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
     * {@code waitUntil(ExpectedConditions.textToBePresentInElement(by, text), 20)}
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
     * {@code waitUntilNot(ExpectedConditions.textToBePresentInElement(by, text))}
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
     * {@code waitUntilNot(ExpectedConditions.textToBePresentInElement(by, text),20)}
     *
     * @param condition
     *            the condition to wait for to become false
     */
    protected <T> void waitUntilNot(ExpectedCondition<T> condition,
            long timeoutInSeconds) {
        waitUntil(ExpectedConditions.not(condition), timeoutInSeconds);
    }

    /**
     * Waits up to 10s for the given element to become present.
     *
     * @param by
     *            the element locator
     */
    protected void waitForElementPresent(final By by) {
        waitUntil(ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * Waits up to 10s for the given element to become not present.
     *
     * @param by
     *            the element locator
     */
    protected void waitForElementNotPresent(final By by) {
        waitUntil(driver -> driver.findElements(by).isEmpty());

    }

    /**
     * Waits up to 10s for the given element to become visible.
     *
     * @param by
     *            the element locator
     */
    protected void waitForElementVisible(final By by) {
        waitUntil(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Checks if the given element has the given class name.
     * <p>
     * Matches only full class names, i.e. has ("foo") does not match
     * class="foobar"
     *
     * @param element
     *            the element to check
     * @param className
     *            the class name to check for
     * @return true if the element has the class name, false otherwise
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

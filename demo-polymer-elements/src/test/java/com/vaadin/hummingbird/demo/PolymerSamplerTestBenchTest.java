package com.vaadin.hummingbird.demo;

import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.vaadin.hummingbird.DemoTestBenchTest;

public abstract class PolymerSamplerTestBenchTest extends DemoTestBenchTest {

    protected boolean isRemote;

    @Override
    public void setupDriver() throws Exception {

        String deploymentUrl = System
                .getProperty("com.vaadin.testbench.deployment.url");
        // deploymentUrl = "http://192.168.2.161:8888/";

        if (deploymentUrl != null && !deploymentUrl.isEmpty()) {
            isRemote = true;
            String hubUrl = "http://tb3-hub.intra.itmill.com:4444/wd/hub";

            DesiredCapabilities capabilities = new DesiredCapabilities(
                    BrowserType.CHROME, "", Platform.LINUX);
                    // capabilities.setCapability("binary",
                    // "/usr/bin/google-chrome");

            // com.vaadin.testbench.deployment.url is based on framework dev
            // server settings, use jetty:run port instead
            deploymentUrl = deploymentUrl.replaceAll(":8888", ":8080");

            setDriver(new RemoteWebDriver(new URL(hubUrl), capabilities));
            baseUrl = deploymentUrl + "?restartApplication";

        } else {
            // use Chrome for local driver because interacting with polymer
            // elements doesn't work with phantomjs
            setDriver(new ChromeDriver());
            baseUrl = "http://localhost:8080/?restartApplication";
        }

        getDriver().manage().window().setSize(new Dimension(1200, 850));
    }

    private String baseUrl;

    // overridden because baseUrl is private in AbstractTestBenchTest...
    @Override
    protected void openUrl(String... parameters) {
        String url = baseUrl;
        if (parameters != null && parameters.length != 0) {
            url += "&" + Arrays.stream(parameters)
                    .collect(Collectors.joining("&"));
        }
        getDriver().get(url);
    }
}

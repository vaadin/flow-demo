package com.vaadin.hummingbird.demo;

import org.openqa.selenium.Dimension;

import com.vaadin.hummingbird.DemoTestBenchTest;

public abstract class PolymerSamplerTestBenchTest extends DemoTestBenchTest {

    @Override
    public void setupDriver() throws Exception {
        super.setupDriver();
        getDriver().manage().window().setSize(new Dimension(1200, 850));
    }

}

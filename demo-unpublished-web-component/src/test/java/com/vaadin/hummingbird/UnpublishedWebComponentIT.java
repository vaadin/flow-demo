package com.vaadin.hummingbird;

import org.junit.Test;

import com.vaadin.hummingbird.DemoTestBenchTest;

public class UnpublishedWebComponentIT extends DemoTestBenchTest {

    @Test
    public void applicationStarts() {
        openUrl("");
        waitForApplicationToStart();
    }

}

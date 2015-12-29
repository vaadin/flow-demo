package com.vaadin.hummingbird.addressbook;

import org.junit.Test;

import com.vaadin.hummingbird.DemoTestBenchTest;

public class AddressBookIT extends DemoTestBenchTest {

    @Test
    public void applicationStarts() {
        openUrl("");
        waitForApplicationToStart();
    }

}

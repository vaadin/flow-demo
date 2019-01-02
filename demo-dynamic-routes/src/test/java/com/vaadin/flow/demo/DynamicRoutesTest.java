/*
 * Copyright 2000-2018 Vaadin Ltd.
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
package com.vaadin.flow.demo;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.demo.testutil.AbstractChromeTest;

public class DynamicRoutesTest extends AbstractChromeTest {

    @Test
    public void loginRegistersSessionRoutes_GlobalCanBeAdded() {
        // opening global with no global registered should redirect to login view.
        openUrl("global");

        WebElement login = findElement(By.id("login-field"));
        WebElement pwd = findElement(By.id("password-field"));

        login.sendKeys("admin");
        pwd.sendKeys("admin");

        // Login as admin.
        findElement(By.id("submit")).click();

        Assert.assertEquals("Should have now admin view visible.", 3,
                findElements(By.tagName("vaadin-checkbox")).size());

        // Time and Version views should have been registered to session on login.
        Assert.assertFalse("Time link should be available",
                findElements(By.id("time-link")).isEmpty());
        Assert.assertFalse("Version link should be available",
                findElements(By.id("version-link")).isEmpty());

        // Register Global to application scope
        findElement(By.id("global-checkbox")).click();

        // Now global should be available
        openUrl("global");

        Assert.assertFalse("Global view should have been available",
                findElements(By.id("remove-global")).isEmpty());

        // as we are still in the session version and view should be available
        openUrl("version");
        Assert.assertFalse("Version view should have been available",
                findElements(By.id("version-return")).isEmpty());

        openUrl("time");
        Assert.assertFalse("Time view should have been available",
                findElements(By.id("time-return")).isEmpty());

        open();

        findElement(By.id("logout")).click();

        // We should be at login view, but have a global view registered
        Assert.assertFalse("Global link should be available on login",
                findElements(By.id("global-link")).isEmpty());

        // now that the session was killed we should not get to time or version views

        openUrl("version");
        Assert.assertTrue("Version view shouldn't be available",
                findElements(By.id("version-return")).isEmpty());
        Assert.assertFalse("Login view should have been displayed",
                findElements(By.id("global-link")).isEmpty());

        openUrl("time");
        Assert.assertTrue("Time view shouldn't be available",
                findElements(By.id("time-return")).isEmpty());
        Assert.assertFalse("Login view should have been displayed",
                findElements(By.id("global-link")).isEmpty());
    }
}

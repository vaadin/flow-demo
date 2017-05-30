/*
 * Copyright 2000-2017 Vaadin Ltd.
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
package com.vaadin.flow.demo.googlesignin.component;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.annotations.Id;
import com.vaadin.flow.demo.testutil.AbstractChromeTest;

/**
 * Tests the basic layout of the demo, which includes the server rendering
 * inside the template by using {@link Id}. The authentication mechanism itself
 * is not tested, because it opens a second window for specific credentials.
 */
public class SigninIT extends AbstractChromeTest {

    @Test
    public void loadPage() {
        open();

        waitForElementPresent(By.tagName("signin-page"));
        WebElement element = findElement(By.tagName("signin-page"));
        List<WebElement> components = findInShadowRoot(element,
                By.id("login-info"));
        Assert.assertFalse(components.isEmpty());

        WebElement h2 = components.get(0).findElement(By.tagName("h2"));
        Assert.assertEquals("Google signin", h2.getText());

        components = findInShadowRoot(element, By.id("glogin"));
        Assert.assertFalse(components.isEmpty());
    }

}

package com.vaadin.hummingbird.demo.webcomponent.progressbubble;
/*
 * Copyright 2000-2016 Vaadin Ltd.
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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.hummingbird.demo.testutil.AbstractDemoTest;

public class ProgressBubbleIT extends AbstractDemoTest {

    @Test
    public void testBubblesShown() {
        open();
        List<WebElement> bubbles = findElements(By.tagName("progress-bubble"));
        Assert.assertEquals(4, bubbles.size());

        WebElement makeProgress = findElement(By.id("makeProgress"));
        makeProgress.click();
        makeProgress.click();

        for (WebElement bubble : bubbles) {
            Assert.assertEquals("10", getPropertyString(bubble, "value"));
        }
    }

    private String getPropertyString(WebElement bubble, String property) {
        return (String) executeScript("return arguments[0][arguments[1]];",
                bubble, property);

    }
}

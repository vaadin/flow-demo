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

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.hummingbird.demo.testutil.AbstractDemoTest;

public class MainViewIT extends AbstractDemoTest {

    @Test
    public void domCorrect() {
        open();
        WebElement bubble = findElements(By.tagName("progress-bubble")).get(1);
        WebElement content = bubble
                .findElement(By.xpath("./div[@id='content']"));
        Assert.assertEquals("62.5 %", content.getText());
    }

    @Test
    public void updatesWork() {
        open();
        WebElement bubble = findElements(By.tagName("progress-bubble")).get(1);
        WebElement content = bubble
                .findElement(By.xpath("./div[@id='content']"));

        executeScript("value.value='10'");
        executeScript("rangeEnd.value='40'");

        Assert.assertEquals(10, getPropertyLong(bubble, "value"));
        Assert.assertEquals("25.0 %", content.getText());
    }

    private long getPropertyLong(WebElement bubble, String property) {
        return (long) executeScript("return arguments[0][arguments[1]];",
                bubble, property);

    }
}

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
package com.vaadin.flow.demo.component.textfield;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.vaadin.flow.demo.testutil.AbstractChromeTest;

public class TextFieldIT extends AbstractChromeTest {

    @Test
    public void basicFunctionality() {
        open();
        WebElement input = findElement(By.xpath("//input"));
        changeInput(input, "14");
        changeInput(input, "44");

        List<WebElement> divs = findElements(By.id("message"));
        Assert.assertEquals("Oh my, 14 is so young!", divs.get(0).getText());

        Assert.assertEquals("Gosh, 44 is so old!", divs.get(1).getText());

    }

    private void changeInput(WebElement input, String string) {
        input.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
                Keys.BACK_SPACE, Keys.BACK_SPACE);
        input.sendKeys(string);
        blur();
    }
}

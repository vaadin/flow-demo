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
package com.vaadin.hummingbird.demo.component.textfield;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.vaadin.hummingbird.demo.testutil.AbstractDemoTest;
import com.vaadin.testbench.By;

public class TextFieldIT extends AbstractDemoTest {

    @Test
    public void basicFunctionality() {
        open();
        WebElement input = findElement(By.xpath("//input"));
        changeInput(input, "14");

        WebElement div = findElement(By.xpath("//body/div[2]"));
        Assert.assertEquals("Oh my, 14 is so young!", div.getText());

        changeInput(input, "44");
        div = findElement(By.xpath("//body/div[3]"));
        Assert.assertEquals("Gosh, 44 is so old!", div.getText());

    }

    private void changeInput(WebElement input, String string) {
        input.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE,
                Keys.BACK_SPACE, Keys.BACK_SPACE);
        input.sendKeys(string);
        findElement(By.xpath("//body")).click();
    }
}

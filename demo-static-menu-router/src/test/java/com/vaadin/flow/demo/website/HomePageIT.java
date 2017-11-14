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
package com.vaadin.flow.demo.website;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.testbench.By;

public class HomePageIT extends AbstractComplexStaticMenuTest {

    @Test
    public void topMenuIsPresent() {
        open();

        Assert.assertTrue("Home link not present",
                isElementPresent(By.className("logo")));
        Assert.assertTrue("Links present",
                isElementPresent(By.className("topnav")));

        Assert.assertTrue("Framework link not present", getMenuItem("Framework").isDisplayed());
        Assert.assertTrue("Elements link not present", getMenuItem("Elements").isDisplayed());
        Assert.assertTrue("Download link not present", getMenuItem("Download").isDisplayed());

        getMenuItem("Framework").click();

        Assert.assertTrue("SubMenu item 'Tutorial' is missing", getSubMenuItem("Tutorial").isDisplayed());

        getMenuItem("Elements").click();

        Assert.assertTrue("SubMenu item 'Demos' is missing", getSubMenuItem("Demos").isDisplayed());

        getMenuItem("Download").click();

        Assert.assertTrue("SubMenu item 'Docs' is missing", getSubMenuItem("Docs").isDisplayed());
        Assert.assertTrue("SubMenu item 'Vaadin Icons' is missing", getSubMenuItem("Vaadin Icons").isDisplayed());

        getSubMenuItem("Vaadin Icons").click();

        Assert.assertTrue("SubSubMenu item 'Icons' is missing", getSubSubMenuItem("Vaadin Icons").isDisplayed());
        Assert.assertTrue("SubSubMenu item 'About' is missing", getSubSubMenuItem("About").isDisplayed());
    }
}

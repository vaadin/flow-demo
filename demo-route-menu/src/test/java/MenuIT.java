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

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.interactions.Actions;

import com.vaadin.flow.testutil.ChromeBrowserTest;
import org.openqa.selenium.By;

public class MenuIT extends ChromeBrowserTest {

    @Override
    protected String getTestPath() {
        return "/";
    }

    @Override
    protected int getDeploymentPort() {
        return 8080;
    }

    @Test
    public void topMenuIsPresent() {
        open();

        Assert.assertTrue("Home link not present",
                isElementPresent(By.linkText("Home")));
        Assert.assertTrue("Internal demos not present",
                isElementPresent(By.linkText("Fancy demos")));
        Assert.assertTrue("External demos not present",
                isElementPresent(By.linkText("External Demos")));

        new Actions(getDriver())
                .moveToElement(findElement(By.linkText("Fancy demos")))
                .perform();

        Assert.assertTrue("Missing internal demo FanOut",
                isElementPresent(By.linkText("FanOut")));
        Assert.assertTrue("Missing internal demo RayTrace",
                isElementPresent(By.linkText("RayTrace")));


        new Actions(getDriver())
                .moveToElement(findElement(By.linkText("External Demos")))
                .perform();
        
        Assert.assertTrue("Missing expected external demo Button",
                isElementPresent(By.linkText("Button")));
        Assert.assertTrue("Missing expected external demo Checkbox",
                isElementPresent(By.linkText("Checkbox")));
        Assert.assertTrue("Missing expected external demo ComboBox",
                isElementPresent(By.linkText("ComboBox")));
        Assert.assertTrue("Missing expected external demo DatePicker",
                isElementPresent(By.linkText("DatePicker")));
        Assert.assertTrue("Missing expected external demo HorizontalLayout",
                isElementPresent(By.linkText("HorizontalLayout")));
        Assert.assertTrue("Missing expected external demo PasswordField",
                isElementPresent(By.linkText("PasswordField")));
        Assert.assertTrue("Missing expected external demo TextArea",
                isElementPresent(By.linkText("TextArea")));
        Assert.assertTrue("Missing expected external demo TextField",
                isElementPresent(By.linkText("TextField")));
        Assert.assertTrue("Missing expected external demo Upload",
                isElementPresent(By.linkText("Upload")));
        Assert.assertTrue("Missing expected external demo VerticalLayout",
                isElementPresent(By.linkText("VerticalLayout")));

    }
}

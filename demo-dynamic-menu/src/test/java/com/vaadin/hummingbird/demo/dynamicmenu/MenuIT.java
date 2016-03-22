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
package com.vaadin.hummingbird.demo.dynamicmenu;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.hummingbird.demo.testutil.AbstractTestBenchTest;

public class MenuIT extends AbstractTestBenchTest {

    private static final String BOOK_OF_DUMMIES = "Book of dummies";
    private static final String THE_LIFE_CHANGER_DEBUGGING = "The life changer: debugging";
    private static final String CHILDRENS_BOOKS = "Children's books";
    private static final String ROMANCE = "Romance";
    private static final String COOKBOOKS = "Cookbooks";

    @Test
    public void expandCollapseCategories() {
        open();

        assertContent("Please select a category in the menu");

        assertCollapsed(CHILDRENS_BOOKS);
        getMenuCategoryLink(CHILDRENS_BOOKS).click();
        assertExpanded(CHILDRENS_BOOKS);
        assertCategorySelected(CHILDRENS_BOOKS);
        assertCategoryViewShown(CHILDRENS_BOOKS);

        assertCollapsed(ROMANCE);
        assertCategoryNotSelected(ROMANCE);
        getMenuCategoryLink(ROMANCE).click();
        assertCollapsed(CHILDRENS_BOOKS);
        assertCategoryNotSelected(CHILDRENS_BOOKS);
        assertCategorySelected(ROMANCE);
        assertExpanded(ROMANCE);

        assertCollapsed(COOKBOOKS);
        assertCategoryNotSelected(COOKBOOKS);
        getMenuCategoryLink(COOKBOOKS).click();
        assertCollapsed(ROMANCE);
        assertCategoryNotSelected(ROMANCE);
        assertCategorySelected(COOKBOOKS);
        assertExpanded(COOKBOOKS);
    }

    @Test
    public void showProductView() {
        open();
        getMenuCategoryLink(CHILDRENS_BOOKS).click();
        assertProductNotSelected(THE_LIFE_CHANGER_DEBUGGING);
        getProductLink(THE_LIFE_CHANGER_DEBUGGING).click();
        assertProductSelected(THE_LIFE_CHANGER_DEBUGGING);
        assertProductViewShown(THE_LIFE_CHANGER_DEBUGGING, 13);

        getProductLink(BOOK_OF_DUMMIES).click();
        assertProductNotSelected(THE_LIFE_CHANGER_DEBUGGING);
        assertProductSelected(BOOK_OF_DUMMIES);
        assertProductViewShown(BOOK_OF_DUMMIES, 63);

    }

    private void assertProductViewShown(String productName, int productId) {
        Assert.assertEquals("Information about product " + productId,
                getStrongContent().getText());
        String content = getContent().getText();
        Assert.assertTrue(content.contains("Title: " + productName));
        Assert.assertTrue(content.contains("Price:"));
        Assert.assertTrue(content.contains("Stock count:"));
    }

    private void assertCategoryViewShown(String category) {
        String header = "Products in " + category;
        WebElement strong = getStrongContent();
        Assert.assertEquals(header, strong.getText());

        List<WebElement> listItems = strong.findElements(By.xpath("../ul/li"));
        Assert.assertTrue(listItems.size() > 5);
        Assert.assertTrue(listItems.get(0).getText().length() > 10);
    }

    private WebElement getStrongContent() {
        return getContent().findElement(By.xpath(".//strong"));
    }

    private void assertContent(String expectedContent) {
        Assert.assertEquals(expectedContent, getContent().getText());
    }

    private void assertExpanded(String category) {
        assertExpanded(category, true);
    }

    private void assertCollapsed(String category) {
        assertExpanded(category, false);
    }

    private void assertExpanded(String category, boolean expanded) {
        WebElement menuItem = getMenuCategoryLink(category)
                .findElement(By.xpath(".."));
        Assert.assertEquals(expanded, hasCssClass(menuItem, "expanded"));
    }

    private void assertCategorySelected(String categoryName) {
        assertCategorySelected(categoryName, true);
    }

    private void assertCategoryNotSelected(String categoryName) {
        assertCategorySelected(categoryName, false);
    }

    private void assertCategorySelected(String categoryName, boolean selected) {
        Assert.assertEquals(selected,
                hasCssClass(getMenuCategoryLink(categoryName), "selected"));
    }

    private void assertProductSelected(String productName) {
        assertProductSelected(productName, true);
    }

    private void assertProductNotSelected(String productName) {
        assertProductSelected(productName, false);
    }

    private void assertProductSelected(String productName, boolean selected) {
        Assert.assertEquals(selected,
                hasCssClass(getProductLink(productName), "selected"));
    }

    private WebElement getContent() {
        return getDriver().findElement(By.className("content"));
    }

    private WebElement getMenuCategoryLink(String text) {
        return getDriver().findElement(By.className("menu"))
                .findElement(By.xpath(".//a[text()=\"" + text + "\"]"));
    }

    private WebElement getProductLink(String productName) {
        return getDriver().findElement(By.className("menu"))
                .findElement(By.xpath(".//a[text()=\"" + productName + "\"]"));

    }

}

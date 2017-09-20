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
package com.vaadin.flow.demo.dynamicmenu;

import java.util.Optional;
import java.util.stream.Stream;

import com.vaadin.flow.demo.dynamicmenu.backend.DataService;
import com.vaadin.flow.demo.dynamicmenu.data.Category;
import com.vaadin.flow.demo.dynamicmenu.data.Product;
import com.vaadin.flow.router.LocationChangeEvent;
import com.vaadin.flow.router.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.common.HtmlContainer;
import com.vaadin.ui.event.Tag;

/**
 * A view which shows products in a given category.
 *
 * @since
 * @author Vaadin Ltd
 */
@Tag("p")
public final class CategoryView extends HtmlContainer implements View {

    private Optional<Category> currentCategory;

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        int catId = getCategoryId(locationChangeEvent);
        currentCategory = DataService.get().getCategoryById(catId);

        if (!currentCategory.isPresent()) {
            setText("Category does not exist or has been removed");
            return;
        }

        removeAll();
        HtmlContainer ul = new HtmlContainer("ul");

        Stream<Product> products = DataService.get().getProducts(catId);
        products.map(Product::getProductName).map(this::createListItem)
                .forEach(ul::add);

        Category category = currentCategory.get();
        HtmlContainer categoryName = new HtmlContainer("strong");
        categoryName.setText("Products in " + category.getName());
        add(categoryName, ul);

    }

    /**
     * Gets the category id based on a location change event.
     *
     * @param locationChangeEvent
     *            a location change event used for finding the URL and
     *            parameters
     * @return the category id or -1 if the URL does not refer to a category
     *         view
     */
    public static int getCategoryId(LocationChangeEvent locationChangeEvent) {
        if (!"category"
                .equals(locationChangeEvent.getLocation().getFirstSegment())) {
            return -1;
        }
        try {
            return Integer.parseInt(locationChangeEvent.getPathParameter("id"));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public String getTitle(LocationChangeEvent locationChangeEvent) {
        if (!currentCategory.isPresent()) {
            return "Unknown category";
        } else {
            return "Category: " + currentCategory.get().getName();
        }
    }

    private Component createListItem(String name) {
        HtmlContainer item = new HtmlContainer("li");
        item.setText(name);
        return item;
    }

}

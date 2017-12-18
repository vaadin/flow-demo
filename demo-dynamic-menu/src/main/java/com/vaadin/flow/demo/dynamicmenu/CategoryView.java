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

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.demo.dynamicmenu.backend.DataService;
import com.vaadin.flow.demo.dynamicmenu.data.Category;
import com.vaadin.flow.demo.dynamicmenu.data.Product;
import com.vaadin.flow.router.BeforeNavigationEvent;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.Route;

/**
 * A view which shows products in a given category.
 *
 * @since
 * @author Vaadin Ltd
 */
@Tag("p")
@Route(value = "category", layout = MainLayout.class)
public final class CategoryView extends HtmlContainer
        implements HasDynamicTitle, HasUrlParameter<Integer> {

    private Optional<Category> currentCategory;

    @Override
    public void setParameter(BeforeNavigationEvent event, Integer parameter) {

        int catId = parameter == null ? -1 : parameter;
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
     * @param location
     *            new navigation location
     * @return the category id or -1 if the URL does not refer to a category
     *         view
     */
    public static int getCategoryId(Location location) {
        if (!"category".equals(location.getFirstSegment())) {
            return -1;
        }
        try {
            List<String> segments = location.getSegments();
            return Integer.parseInt(segments.get(segments.size() - 1));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private Component createListItem(String name) {
        HtmlContainer item = new HtmlContainer("li");
        item.setText(name);
        return item;
    }

    @Override
    public String getPageTitle() {
        if (!currentCategory.isPresent()) {
            return "Unknown category";
        } else {
            return "Category: " + currentCategory.get().getName();
        }
    }
}

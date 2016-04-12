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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.vaadin.hummingbird.demo.dynamicmenu.backend.DataService;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Category;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Product;
import com.vaadin.hummingbird.html.Div;
import com.vaadin.hummingbird.html.HtmlContainer;
import com.vaadin.hummingbird.router.LocationChangeEvent;

/**
 * Menu displaying categories as groups and products as items in the groups.
 *
 * @author Vaadin
 * @since
 */
public final class Menu extends Div {

    private Map<Integer, CategoryComponent> categories = new HashMap<>();

    /**
     * Creates the menu.
     */
    public Menu() {
        setClassName("menu");
        HtmlContainer ul = new HtmlContainer("ul");
        add(ul);
        for (Category c : DataService.get().getAllCategories()) {
            CategoryComponent item = new CategoryComponent(c.getId(),
                    c.getName());
            categories.put(c.getId(), item);
            ul.add(item);
        }
    }

    /**
     * Called when the location changes so the menu can be updated based on the
     * currently shown view.
     *
     * @param locationChangeEvent
     *            the location change event
     */
    public void update(LocationChangeEvent locationChangeEvent) {
        int categoryId = CategoryView.getCategoryId(locationChangeEvent);
        int productId = ProductView.getProductId(locationChangeEvent);
        if (categoryId == -1 && productId != -1) {
            Optional<Product> p = DataService.get().getProductById(productId);
            if (p.isPresent()) {
                categoryId = p.get().getCategory().getId();
            }
        }

        if (categoryId != -1) {
            Optional<CategoryComponent> category = getElementForCategory(
                    DataService.get().getCategoryById(categoryId));
            if (category.isPresent()) {
                categories.values().stream().filter(e -> e != category.get())
                        .forEach(e -> selectCategory(e, false));
                selectCategory(category.get(), true);
                category.get().selectProduct(productId);
            }
        }
    }

    private void selectCategory(CategoryComponent category, boolean select) {
        if (select) {
            category.expand();
            category.select(true);
        } else {
            category.collapse();
            category.select(false);
        }
    }

    private Optional<CategoryComponent> getElementForCategory(
            Optional<Category> category) {
        if (!category.isPresent()) {
            return Optional.empty();
        }

        return Optional.ofNullable(categories.get(category.get().getId()));
    }

}

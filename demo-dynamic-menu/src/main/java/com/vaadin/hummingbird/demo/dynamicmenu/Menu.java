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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.vaadin.hummingbird.StateNode;
import com.vaadin.hummingbird.demo.dynamicmenu.backend.DataService;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Category;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Product;
import com.vaadin.hummingbird.nodefeature.ModelMap;
import com.vaadin.hummingbird.router.LocationChangeEvent;
import com.vaadin.hummingbird.router.Router;
import com.vaadin.hummingbird.router.RouterLink;
import com.vaadin.ui.Template;
import com.vaadin.ui.UI;

/**
 * Menu displaying categories as groups and products as items in the groups.
 *
 * @author Vaadin
 * @since
 */
public class Menu extends Template {

    private int selectedCategoryId = -1;

    /**
     * Creates the menu.
     */
    public Menu() {
        createCategories();
    }

    private void createCategories() {
        Collection<Category> categories = DataService.get().getAllCategories();
        List<StateNode> categoryItems = null;
        if (!categories.isEmpty()) {
            categoryItems = new ArrayList<>();
            Router router = UI.getCurrent().getRouter().get();

            categories.forEach(
                    c -> categoryItems.add(createCategoryItem(router, c)));

        }
        getElement().getNode().getFeature(ModelMap.class).setValue("items",
                categoryItems);
    }

    private StateNode createCategoryItem(Router router, Category category) {
        return createMenuItem(
                RouterLink.buildUrl(router, CategoryView.class,
                        Integer.toString(category.getId())),
                category.getName(), false);
    }

    private StateNode createProductItem(Router router, Product product,
            boolean selected) {
        return createMenuItem(
                RouterLink.buildUrl(router, ProductView.class,
                        Integer.toString(product.getId())),
                product.getProductName(), selected);
    }

    private StateNode createMenuItem(String href, String caption,
            boolean selected) {
        StateNode menuItem = new StateNode(ModelMap.class);
        menuItem.getFeature(ModelMap.class).setValue("href", href);
        menuItem.getFeature(ModelMap.class).setValue("caption", caption);
        // should be NOOP if selected == false
        menuItem.getFeature(ModelMap.class).setValue("selected", selected);

        return menuItem;
    }

    private List<StateNode> getCategoryItems() {
        return getElement().getNode().getFeature(ModelMap.class)
                .getValue("items");
    }

    private int getCategoryId(StateNode category) {
        String href = category.getFeature(ModelMap.class).getValue("href");
        return Integer.parseInt(href.substring(href.indexOf("/")));
    }

    /**
     * Called when the location changes so the menu can be updated based on the
     * currently shown view.
     *
     * @param locationChangeEvent
     *            the location change event
     */
    public void update(LocationChangeEvent locationChangeEvent) {
        final int categoryId;
        int productId = ProductView.getProductId(locationChangeEvent);
        if (productId != -1) {
            Optional<Product> p = DataService.get().getProductById(productId);
            if (p.isPresent()) {
                categoryId = p.get().getCategory().getId();
            } else {
                categoryId = -1;
            }
        } else {
            categoryId = CategoryView.getCategoryId(locationChangeEvent);
        }

        // as the previously selected category is always cleared, no need to
        // clear previously selected product item
        if (selectedCategoryId != -1) {
            getCategoryItem(selectedCategoryId)
                    .ifPresent(this::collapseCategory);
        }

        if (categoryId != -1) {
            DataService.get().getCategoryById(categoryId)
                    .flatMap(this::getCategoryItem)
                    .ifPresent(item -> expandCategory(item, categoryId,
                            productId));
        }
    }

    private void expandCategory(StateNode categoryItem, int categoryId,
            int productId) {
        categoryItem.getFeature(ModelMap.class).setValue("selected", true);

        Stream<Product> products = DataService.get().getProducts(categoryId);
        Router router = getUI().get().getRouter().get();
        List<StateNode> submenuItems = new ArrayList<>();

        products.forEach(p -> submenuItems
                .add(createProductItem(router, p, p.getId() == productId)));

        categoryItem.getFeature(ModelMap.class).setValue("subitems",
                submenuItems);

        selectedCategoryId = categoryId;
    }

    private void collapseCategory(StateNode categoryItem) {
        categoryItem.getFeature(ModelMap.class).setValue("selected", false);
        categoryItem.getFeature(ModelMap.class).setValue("subitems", null);
        selectedCategoryId = -1;
    }

    private Optional<StateNode> getCategoryItem(Category category) {
        return getCategoryItem(category.getId());
    }

    private Optional<StateNode> getCategoryItem(int categoryId) {
        List<StateNode> categories = getCategoryItems();
        return categories.stream().filter(c -> getCategoryId(c) != categoryId)
                .findFirst();

    }

}

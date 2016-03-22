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
import java.util.stream.Stream;

import com.vaadin.hummingbird.demo.dynamicmenu.backend.DataService;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Category;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Product;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.router.Location;
import com.vaadin.hummingbird.router.LocationChangeEvent;

/**
 * Menu displaying categories as groups and products as items in the groups.
 *
 * @author Vaadin
 * @since
 */
public class Menu {

    private static final String CLASS_SELECTED = "selected";
    private static final String CLASS_EXPANDED = "expanded";
    private static final String CLASS_COLLAPSED = "collapsed";
    private Element element;
    private Map<Integer, Element> categoryLinks = new HashMap<>();
    private Map<Integer, Element> productLinks = new HashMap<>();

    /**
     * Creates the menu.
     */
    public Menu() {
        element = ElementFactory.createDiv();
        element.getClassList().add("menu");
        Element ul = ElementFactory.createUnorderedList();
        element.appendChild(ul);
        for (Category c : DataService.get().getAllCategories()) {
            Element categoryLink = ElementFactory
                    .createRouterLink("category/" + c.getId(), c.getName());
            categoryLinks.put(c.getId(), categoryLink);
            Element li = ElementFactory.createListItem()
                    .appendChild(categoryLink);
            setExpandedClass(li, false);
            ul.appendChild(li);
        }
    }

    private void collapse(Element listItem) {
        // First child is the link, remove everything else
        while (listItem.getChildCount() > 1) {
            listItem.removeChild(1);
        }
        setExpandedClass(listItem, false);
    }

    private static void setExpandedClass(Element listItem, boolean expanded) {
        listItem.getClassList().set(CLASS_EXPANDED, expanded);
        listItem.getClassList().set(CLASS_COLLAPSED, !expanded);
    }

    private void expand(Element listItemElement) {
        if (isExpanded(listItemElement)) {
            return;
        }
        productLinks.clear();

        setExpandedClass(listItemElement, true);

        // The link is always the first child, the sub menu is the second
        Element routerLinkElement = listItemElement.getChild(0);
        Element ul = ElementFactory.createUnorderedList();
        listItemElement.appendChild(ul);

        String categoryId = routerLinkElement.getAttribute("href")
                .replace("category/", "");
        Stream<Product> products = DataService.get()
                .getProducts(Integer.parseInt(categoryId));
        products.forEach(p -> {
            Element productLink = ElementFactory.createRouterLink(
                    "product/" + p.getId(), p.getProductName());
            Element li = ElementFactory.createListItem()
                    .appendChild(productLink);
            ul.appendChild(li);

            productLinks.put(p.getId(), productLink);
        });
    }

    private boolean isExpanded(Element listItem) {
        return listItem.getChildCount() > 1;
    }

    public Element getElement() {
        return element;
    }

    /**
     * Called when the location changes so the menu can be updated based on the
     * currently shown view.
     *
     * @param locationChangeEvent
     *            the location change event
     */
    public void update(LocationChangeEvent locationChangeEvent) {
        Location location = locationChangeEvent.getLocation();
        if (!location.hasSegments()) {
            collapseAll();
            return;
        }
        int categoryId = CategoryView.getCategoryId(locationChangeEvent);
        int productId = ProductView.getProductId(locationChangeEvent);
        if (categoryId == -1 && productId != -1) {
            Optional<Product> p = DataService.get().getProductById(productId);
            if (p.isPresent()) {
                categoryId = p.get().getCategory().getId();
            }
        }

        if (categoryId != -1) {
            Optional<Element> categoryLinkElement = getElementForCategory(
                    DataService.get().getCategoryById(categoryId));
            if (categoryLinkElement.isPresent()) {
                categoryLinks.values().stream()
                        .filter(e -> e != categoryLinkElement.get())
                        .forEach(e -> {
                            collapse(e.getParent());
                            deselectLink(e);
                        });

                expand(categoryLinkElement.get().getParent());
                selectLink(categoryLinkElement.get());
            }
        }

        if (productId != -1) {
            Optional<Element> productElement = getElementForProduct(
                    DataService.get().getProductById(productId));
            if (productElement.isPresent()) {
                productLinks.values().forEach(Menu::deselectLink);
                selectLink(productElement.get());
            }
        }

    }

    private static void selectLink(Element linkElement) {
        linkElement.getClassList().add(CLASS_SELECTED);
    }

    private static void deselectLink(Element linkElement) {
        linkElement.getClassList().remove(CLASS_SELECTED);
    }

    private void collapseAll() {
        categoryLinks.values().forEach(this::collapse);

    }

    private Optional<Element> getElementForCategory(
            Optional<Category> category) {
        if (!category.isPresent()) {
            return Optional.empty();
        }

        return Optional.ofNullable(categoryLinks.get(category.get().getId()));
    }

    private Optional<Element> getElementForProduct(Optional<Product> product) {
        if (!product.isPresent()) {
            return Optional.empty();
        }

        return Optional.ofNullable(productLinks.get(product.get().getId()));
    }

}

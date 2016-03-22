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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.hummingbird.demo.dynamicmenu.backend.DataService;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Category;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Product;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.router.Location;
import com.vaadin.hummingbird.router.LocationChangeEvent;

public class Menu {

    private Element element;
    private Map<Integer, Element> categoryLinks = new HashMap<>();
    private Map<Integer, Element> productLinks = new HashMap<>();

    public Menu() {
        element = ElementFactory.createDiv();
        element.getClassList().add("menu");
        Element ul = ElementFactory.createUnsortedList();
        element.appendChild(ul);
        for (Category c : DataService.get().getAllCategories()) {
            Element categoryLink = ElementFactory
                    .createRouterLink("category/" + c.getId(), c.getName());
            categoryLinks.put(c.getId(), categoryLink);
            Element li = ElementFactory.createListItem()
                    .appendChild(categoryLink);
            li.getClassList().add("collapsed");
            ul.appendChild(li);
        }
    }

    private int getChildIndex(Element e) {
        return e.getParent().getChildren().collect(Collectors.toList())
                .indexOf(e);
    }

    public void collapse(Element routerLinkElement) {
        Element parent = routerLinkElement.getParent();
        while (parent.getChildCount() > 1) {
            parent.removeChild(1);
        }
        routerLinkElement.getParent().getClassList().set("expanded", false);
        routerLinkElement.getParent().getClassList().set("collapsed", true);

    }

    public void expand(Element routerLinkElement) {
        if (isExpanded(routerLinkElement)) {
            return;
        }
        productLinks.clear();

        routerLinkElement.getParent().getClassList().set("expanded", true);
        routerLinkElement.getParent().getClassList().set("collapsed", false);
        Element ul = ElementFactory.createUnsortedList();
        int index = getChildIndex(routerLinkElement);
        routerLinkElement.getParent().insertChild(index + 1, ul);

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

    private boolean isExpanded(Element e) {
        return e.getParent().getChildCount() > 1;
    }

    public Element getElement() {
        return element;
    }

    public void update(LocationChangeEvent locationChangeEvent) {
        Location location = locationChangeEvent.getLocation();
        if (!location.hasSegments()) {
            collapseAll();
            return;
        }
        int categoryId = CategoryView.getCategoryId(locationChangeEvent);
        int productId = ProductView.getProductId(locationChangeEvent);
        if (categoryId == -1 && productId != -1) {
            categoryId = getCategoryId(productId);
        }

        if (categoryId != -1) {
            Optional<Element> catElement = getElementForCategory(
                    DataService.get().getCategoryById(categoryId));
            if (catElement.isPresent()) {
                categoryLinks.values().stream()
                        .filter(e -> e != catElement.get()).forEach(e -> {
                            collapse(e);
                            deselect(e);
                        });

                expand(catElement.get());
                select(catElement.get());
            }
        }

        if (productId != -1) {
            Optional<Element> productElement = getElementForProduct(
                    DataService.get().getProductById(productId));
            if (productElement.isPresent()) {
                productLinks.values().forEach(this::deselect);
                select(productElement.get());
            }
        }

    }

    private void select(Element e) {
        e.getClassList().add("selected");
    }

    private void deselect(Element e) {
        e.getClassList().remove("selected");
    }

    private int getCategoryId(int productId) {
        return DataService.get().getProductById(productId)
                .map(Product::getCategory).map(Category::getId).orElse(-1);
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

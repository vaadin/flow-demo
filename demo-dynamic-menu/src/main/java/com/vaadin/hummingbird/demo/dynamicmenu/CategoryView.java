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

import java.util.Optional;
import java.util.stream.Stream;

import com.vaadin.hummingbird.demo.dynamicmenu.backend.DataService;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Category;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Product;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.router.LocationChangeEvent;

/**
 * A view which shows products in a given category.
 *
 * @since
 * @author Vaadin Ltd
 */
public class CategoryView extends SimpleView {

    /**
     * Creates a new view.
     */
    public CategoryView() {
        super(ElementFactory.createParagraph(""));
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        int catId = getCategoryId(locationChangeEvent);
        Optional<Category> category = DataService.get().getCategoryById(catId);
        if (!category.isPresent()) {
            getElement().setTextContent(
                    "Category does not exist or has been removed");
            return;
        }

        getElement().removeAllChildren();
        getElement().appendChild(ElementFactory
                .createStrong("Products in " + category.get().getName()));

        Element ul = ElementFactory.createUnorderedList();

        Stream<Product> products = DataService.get().getProducts(catId);
        products.map(Product::getProductName)
                .map(ElementFactory::createListItem).forEach(ul::appendChild);

        getElement().appendChild(ul);

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

}

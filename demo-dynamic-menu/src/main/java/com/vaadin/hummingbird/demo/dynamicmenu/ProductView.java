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

import java.text.DecimalFormat;
import java.util.Optional;

import com.vaadin.hummingbird.demo.dynamicmenu.backend.DataService;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Product;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.router.LocationChangeEvent;

/**
 * A view which shows a product.
 *
 * @since
 * @author Vaadin Ltd
 */
public class ProductView extends SimpleView {

    /**
     * Creates a new view.
     */
    public ProductView() {
        super(ElementFactory.createParagraph(""));
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        int productId = getProductId(locationChangeEvent);
        Optional<Product> maybeProduct = DataService.get()
                .getProductById(productId);
        if (!maybeProduct.isPresent()) {
            getElement().setTextContent("Invalid id");
            return;
        }

        Product product = maybeProduct.get();
        getElement().removeAllChildren();
        getElement().appendChild(ElementFactory
                .createStrong("Information about product " + product.getId()));
        getElement().appendChild(
                ElementFactory.createDiv("Title: " + product.getProductName()));
        getElement()
                .appendChild(ElementFactory.createDiv("Price: " + DecimalFormat
                        .getCurrencyInstance().format(product.getPrice())));
        getElement().appendChild(ElementFactory
                .createDiv("Stock count: " + product.getStockCount()));

    }

    public static int getProductId(LocationChangeEvent locationChangeEvent) {
        if (!locationChangeEvent.getLocation().hasSegments()) {
            return -1;
        }

        if (!"product"
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

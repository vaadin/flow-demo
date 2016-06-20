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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.hummingbird.demo.dynamicmenu.data.Product;
import com.vaadin.hummingbird.template.model.TemplateModel;
import com.vaadin.ui.AttachEvent;
import com.vaadin.ui.Template;

/**
 * Shows a list of products in a category.
 * 
 * @author Vaadin Ltd
 *
 */
public class ProductsListComponent extends Template {

    public interface ProductModel {
        String getLink();

        void setLink(String link);

        String getProductName();

        boolean isSelected();

        void setSelected(boolean selected);

        int getId();
    }

    public static interface ProductsListModel extends TemplateModel {
        void setProducts(List<ProductModel> products);

        List<ProductModel> getProducts();
    }

    /**
     * Creates a products list instance for given stream of {@code products}.
     * 
     * @param products
     *            a stream of products
     */
    public ProductsListComponent(Stream<Product> products) {
        Set<String> properties = new HashSet<>(
                Arrays.asList("id", "productName"));
        getModel().importBeans("products",
                products.collect(Collectors.toList()), Product.class,
                property -> properties.contains(property));
        getModel().getProducts().forEach(this::initProduct);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
    }

    @Override
    protected ProductsListModel getModel() {
        return (ProductsListModel) super.getModel();
    }

    /**
     * Selects a product by its {@code productId}.
     * 
     * @param productId
     *            product id
     */
    public void select(int productId) {
        if (productId != -1) {
            getModel().getProducts().forEach(product -> product
                    .setSelected(product.getId() == productId));
        }
    }

    private void initProduct(ProductModel model) {
        model.setLink("product/" + model.getId());
        model.setSelected(false);
    }
}

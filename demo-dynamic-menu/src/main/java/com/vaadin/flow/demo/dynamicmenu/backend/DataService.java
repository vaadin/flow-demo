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
package com.vaadin.flow.demo.dynamicmenu.backend;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import com.vaadin.flow.demo.dynamicmenu.data.Category;
import com.vaadin.flow.demo.dynamicmenu.data.Product;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public interface DataService {

    /**
     * Gets all products.
     *
     * @return all products
     */
    Collection<Product> getAllProducts();

    /**
     * Gets all categories.
     *
     * @return all categories
     */
    Collection<Category> getAllCategories();

    /**
     * Gets the product with the given id.
     *
     * @param productId
     *            the product id
     * @return the product with the given id
     */
    Optional<Product> getProductById(int productId);

    /**
     * Gets the category with the given id.
     *
     * @param categoryId
     *            the category id
     * @return the category with the given id
     */
    Optional<Category> getCategoryById(int categoryId);

    /**
     * Gets all products which belongs to the given category.
     *
     * @param categoryId
     *            the category id
     * @return all products belonging to the given category
     */
    Stream<Product> getProducts(int categoryId);

    /**
     * Gets the singleton data service instance.
     *
     * @return the only data service instance
     */
    static DataService get() {
        return MockDataService.get();
    }
}

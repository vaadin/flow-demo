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

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.vaadin.flow.demo.dynamicmenu.data.Category;
import com.vaadin.flow.demo.dynamicmenu.data.Product;

/**
 * A simple data model.
 */
public class MockDataService implements DataService {

    private static final MockDataService INSTANCE = new MockDataService();

    private List<Product> products;
    private List<Category> categories;

    private MockDataService() {
        categories = MockDataGenerator.createCategories();
        products = MockDataGenerator.createProducts(categories);
    }

    /**
     * Gets the singleton instance.
     *
     * @return the only instance
     */
    public static synchronized MockDataService get() {
        return INSTANCE;
    }

    @Override
    public synchronized List<Product> getAllProducts() {
        return products;
    }

    @Override
    public synchronized List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public Stream<Product> getProducts(int categoryId) {
        return getAllProducts().stream()
                .filter(p -> p.getCategory().getId() == categoryId);
    }

    @Override
    public synchronized Optional<Category> getCategoryById(int catId) {
        return getAllCategories().stream().filter(c -> c.getId() == catId)
                .findFirst();
    }

    @Override
    public synchronized Optional<Product> getProductById(int productId) {
        return getAllProducts().stream().filter(p -> p.getId() == productId)
                .findFirst();
    }
}

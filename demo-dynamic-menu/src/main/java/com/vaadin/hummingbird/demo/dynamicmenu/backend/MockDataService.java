package com.vaadin.hummingbird.demo.dynamicmenu.backend;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.vaadin.hummingbird.demo.dynamicmenu.data.Category;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Product;

/**
 * Mock data model. This implementation has very simplistic locking and does not
 * notify users of modifications.
 */
public class MockDataService implements DataService {

    private static MockDataService INSTANCE;

    private List<Product> products;
    private List<Category> categories;
    private int nextProductId = 0;

    private MockDataService() {
        categories = MockDataGenerator.createCategories();
        products = MockDataGenerator.createProducts(categories);
        nextProductId = products.size() + 1;
    }

    public synchronized static DataService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MockDataService();
        }
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
    public synchronized void updateProduct(Product p) {
        if (p.getId() < 0) {
            // New product
            p.setId(nextProductId++);
            products.add(p);
            return;
        }
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == p.getId()) {
                products.set(i, p);
                return;
            }
        }

        throw new IllegalArgumentException(
                "No product with id " + p.getId() + " found");
    }

    @Override
    public synchronized void deleteProduct(int productId) {
        Optional<Product> p = getProductById(productId);
        if (!p.isPresent()) {
            throw new IllegalArgumentException(
                    "Product with id " + productId + " not found");
        }
        products.remove(p);
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

package com.vaadin.hummingbird.demo.dynamicmenu.backend;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import com.vaadin.hummingbird.demo.dynamicmenu.data.Category;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Product;

/**
 * Back-end service interface for retrieving and updating product data.
 */
public interface DataService {

    public abstract Collection<Product> getAllProducts();

    public abstract Collection<Category> getAllCategories();

    public abstract void updateProduct(Product p);

    public abstract void deleteProduct(int productId);

    public abstract Optional<Product> getProductById(int productId);

    public abstract Optional<Category> getCategoryById(int catId);

    public abstract Stream<Product> getProducts(int categoryId);

    public static DataService get() {
        return MockDataService.getInstance();
    }

}
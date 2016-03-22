package com.vaadin.hummingbird.demo.dynamicmenu.data;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {

    private int id = -1;
    private String productName = "";
    private BigDecimal price = BigDecimal.ZERO;
    private Category category;
    private int stockCount = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

}

package com.vaadin.hummingbird.demo.dynamicmenu.data;

import java.io.Serializable;

/**
 * Data describing a product category.
 *
 * @author Vaadin
 * @since
 */
public class Category implements Serializable {

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}

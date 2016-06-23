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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaadin.annotations.Include;
import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.demo.dynamicmenu.backend.DataService;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Category;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Product;
import com.vaadin.hummingbird.html.HtmlContainer;
import com.vaadin.hummingbird.router.LocationChangeEvent;
import com.vaadin.hummingbird.router.View;
import com.vaadin.hummingbird.template.model.TemplateModel;
import com.vaadin.ui.Template;

/**
 * A view which shows products in a given category.
 *
 * @since
 * @author Vaadin Ltd
 */
@Tag("p")
public final class CategoryView extends HtmlContainer implements View {

    private Optional<Category> currentCategory;

    public interface ProductModel extends TemplateModel {

        @Include("productName")
        void setProducts(List<Product> products);

    }

    public static class CategorySection extends Template {

        @Override
        protected ProductModel getModel() {
            return (ProductModel) super.getModel();
        }
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        int catId = getCategoryId(locationChangeEvent);
        currentCategory = DataService.get().getCategoryById(catId);

        if (!currentCategory.isPresent()) {
            setText("Category does not exist or has been removed");
            return;
        }

        removeAll();
        CategorySection section = new CategorySection();
        section.getModel().setProducts(DataService.get().getProducts(catId)
                .collect(Collectors.toList()));

        Category category = currentCategory.get();
        HtmlContainer categoryName = new HtmlContainer("strong");
        categoryName.setText("Products in " + category.getName());
        add(categoryName, section);

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

    @Override
    public String getTitle(LocationChangeEvent locationChangeEvent) {
        if (!currentCategory.isPresent()) {
            return "Unknown category";
        } else {
            return "Category: " + currentCategory.get().getName();
        }
    }

}

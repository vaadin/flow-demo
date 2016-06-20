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
import java.util.function.Predicate;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.demo.dynamicmenu.backend.DataService;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Category;
import com.vaadin.hummingbird.demo.dynamicmenu.data.Product;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.nodefeature.ModelMap;
import com.vaadin.hummingbird.router.LocationChangeEvent;
import com.vaadin.hummingbird.template.model.TemplateModel;
import com.vaadin.ui.Template;

/**
 * Layout showing the main menu above a sub view.
 *
 * @since
 * @author Vaadin Ltd
 */
@StyleSheet("css/site.css")
public final class MainLayout extends Template {

    private ProductsListComponent products;

    public interface CategoryModel {
        String getLink();

        void setLink(String link);

        String getName();

        boolean isSelected();

        void setSelected(boolean selected);

        boolean isExpanded();

        void setExpanded(boolean expanded);

        int getId();
    }

    public static interface CategoriesListModel extends TemplateModel {
        void setCategories(List<CategoryModel> products);

        List<CategoryModel> getCategories();
    }

    /**
     * Creates a new layout.
     */
    public MainLayout() {
        getModel().importBeans("categories",
                DataService.get().getAllCategories(), Category.class,
                property -> true);
        getModel().getCategories().forEach(this::initCategory);
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        update(locationChangeEvent);
    }

    @Override
    protected CategoriesListModel getModel() {
        return (CategoriesListModel) super.getModel();
    }

    private void initCategory(CategoryModel model) {
        model.setLink("category/" + model.getId());
        model.setSelected(false);
        model.setExpanded(false);
    }

    /**
     * Called when the location changes so the menu can be updated based on the
     * currently shown view.
     *
     * @param locationChangeEvent
     *            the location change event
     */
    private void update(LocationChangeEvent locationChangeEvent) {
        int categoryId = CategoryView.getCategoryId(locationChangeEvent);
        int productId = ProductView.getProductId(locationChangeEvent);
        if (categoryId == -1 && productId != -1) {
            Optional<Product> p = DataService.get().getProductById(productId);
            if (p.isPresent()) {
                categoryId = p.get().getCategory().getId();
            }
        }

        if (categoryId != -1) {
            int catId = categoryId;
            int prodId = productId;
            Optional<Element> category = getElementForCategory(categoryId);
            if (category.isPresent()) {
                getModel().getCategories().forEach(cat -> selectCategory(cat,
                        catId, prodId, category.get()));
            }
        }
    }

    private Optional<Element> getElementForCategory(int id) {
        Optional<Element> div = getFirstChild(getElement(), Tag.DIV);
        if (!div.isPresent()) {
            return Optional.empty();
        }
        Optional<Element> ul = getFirstChild(div.get(), Tag.UL);
        if (!ul.isPresent()) {
            return Optional.empty();
        }
        return ul.get().getChildren().filter(child -> {
            ModelMap feature = child.getNode().getFeature(ModelMap.class);
            return feature.getValue("id").equals(id);
        }).findFirst();
    }

    private Optional<Element> getFirstChild(Element element, String tag) {
        Predicate<Element> isTextNode = Element::isTextNode;
        return element.getChildren().filter(isTextNode.negate())
                .filter(child -> tag.equals(child.getTag())).findFirst();
    }

    private void selectCategory(CategoryModel model, int categoryId,
            int productId, Element categoryElement) {
        if (model.getId() == categoryId) {
            expand(model, categoryElement);
            selectProduct(model, productId);
        } else {
            collapse(model, categoryElement);
        }
        model.setSelected(model.getId() == categoryId);
    }

    private void expand(CategoryModel model, Element categoryElement) {
        if (model.isExpanded()) {
            return;
        }

        model.setExpanded(true);

        Optional<Element> routerLink = getFirstChild(categoryElement, Tag.A);
        // TODO: Use Element.as(RouterLink.class) once #1034 is fixed
        String categoryId = routerLink.get().getAttribute("href")
                .replace("category/", "");
        products = new ProductsListComponent(
                DataService.get().getProducts(Integer.parseInt(categoryId)));

        getFirstChild(categoryElement, Tag.DIV)
                .ifPresent(div -> div.appendChild(products.getElement()));
    }

    private void collapse(CategoryModel model, Element categoryElement) {
        if (!model.isExpanded()) {
            return;
        }

        getFirstChild(categoryElement, Tag.DIV)
                .ifPresent(Element::removeAllChildren);
        model.setExpanded(false);
    }

    private void selectProduct(CategoryModel model, int productId) {
        if (!model.isExpanded()) {
            throw new IllegalStateException(
                    "Category is not expanded. Cannot select product by id in the collapsed category");
        }
        assert products != null;
        products.select(productId);
    }
}

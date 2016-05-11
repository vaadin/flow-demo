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
package com.vaadin.hummingbird.demo.website;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.hummingbird.router.View;
import com.vaadin.ui.Template;
import com.vaadin.ui.UI;

/**
 * Layout showing the main menu above a sub view.
 *
 * @since
 * @author Vaadin Ltd
 */
public final class MainLayout extends Template implements View {

    /**
     * Creates a new layout and menu.
     */
    public MainLayout() {
        UI.getCurrent().getPage().addStyleSheet("css/site.css");

        createMenu();
    }

    public static class MenuItem {
        private String href, caption;

        public MenuItem(String caption, Class<? extends View> viewClass,
                String... viewParameters) {
            href = getUrl(viewClass, viewParameters);
            this.caption = caption;
        }

        private static String getUrl(Class<? extends View> viewClass,
                String[] viewParameters) {
            return "TODO Use router easily, maybe extract logic from RouterLink";
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }
    }

    public static class MyModel {
        private List<MenuItem> items;

        public List<MenuItem> getItems() {
            return items;
        }

        public void setItems(List<MenuItem> items) {
            this.items = items;
        }
    }

    protected MyModel getModel() {
        return (MyModel) super.getModel();
    }

    private void createMenu() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("About", AboutView.class));
        menuItems.add(new MenuItem("Parameter view", ParameterView.class, "1"));
        menuItems.add(new MenuItem("Resource view", ResourcesView.class));
        menuItems.add(new MenuItem("Dynamic resource view",
                DynamicResourcesView.class));

        getModel().setItems(menuItems);
    }
}

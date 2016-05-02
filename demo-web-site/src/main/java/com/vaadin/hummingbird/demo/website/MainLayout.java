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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.hummingbird.nodefeature.ModelMap;
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
     * Model for menu items.
     */
    public static class MenuItem implements Serializable {
        private final String href;
        private final String caption;

        /**
         * Creates a new menu item.
         *
         * @param href
         *            the href to link to
         * @param caption
         *            the caption to display
         */
        public MenuItem(String href, String caption) {
            this.href = href;
            this.caption = caption;
        }

        /**
         * Gets the href of the menu item.
         *
         * @return the href
         */
        public String getHref() {
            return href;
        }

        /**
         * Gets the caption of the menu item.
         *
         * @return the caption
         */
        public String getCaption() {
            return caption;
        }
    }

    /**
     * Creates a new layout and menu.
     */
    public MainLayout() {
        UI.getCurrent().getPage().addStyleSheet("css/site.css");

        createMenu();
    }

    private void createMenu() {
        List<MenuItem> items = getMenuItems();

        ModelMap model = getElement().getNode().getFeature(ModelMap.class);

        model.setValue("items", items);
    }

    private List<MenuItem> getMenuItems() {
        ArrayList<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem("about", "About"));
        items.add(new MenuItem("param/1", "Parameter view"));
        items.add(new MenuItem("resource/", "Resource view"));
        items.add(new MenuItem("dynresource", "Dynamic resource view"));
        return items;
    }
}

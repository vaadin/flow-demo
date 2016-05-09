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

import com.vaadin.hummingbird.StateNode;
import com.vaadin.hummingbird.nodefeature.ModelList;
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
     * Creates a new layout and menu.
     */
    public MainLayout() {
        UI.getCurrent().getPage().addStyleSheet("css/site.css");

        createMenu();
    }

    private void createMenu() {
        StateNode items = createMenuItems();

        ModelMap model = getElement().getNode().getFeature(ModelMap.class);

        model.setValue("items", items);
    }

    private StateNode createMenuItems() {
        StateNode items = new StateNode(ModelList.class);
        ModelList itemsList = items.getFeature(ModelList.class);
        itemsList.add(createMenuItem("about", "About"));
        itemsList.add(createMenuItem("param/1", "Parameter view"));
        itemsList.add(createMenuItem("resource/", "Resource view"));
        itemsList.add(createMenuItem("dynresource", "Dynamic resource view"));
        return items;
    }

    private StateNode createMenuItem(String href, String caption) {
        StateNode menuItem = new StateNode(ModelMap.class);
        menuItem.getFeature(ModelMap.class).setValue("href", href);
        menuItem.getFeature(ModelMap.class).setValue("caption", caption);
        return menuItem;
    }
}

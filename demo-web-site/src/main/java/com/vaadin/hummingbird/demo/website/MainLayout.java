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

import com.vaadin.annotations.StyleSheet;
import com.vaadin.hummingbird.StateNode;
import com.vaadin.hummingbird.nodefeature.ModelList;
import com.vaadin.hummingbird.nodefeature.ModelMap;
import com.vaadin.hummingbird.router.LocationChangeEvent;
import com.vaadin.hummingbird.router.View;
import com.vaadin.ui.Template;

/**
 * Layout showing the main menu above a sub view.
 *
 * @since
 * @author Vaadin Ltd
 */
@StyleSheet("css/site.css")
public final class MainLayout extends Template implements View {

    /**
     * Creates a new layout and menu.
     */
    public MainLayout() {
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
        menuItem.getFeature(ModelMap.class).setValue("active", false);
        return menuItem;
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        String firstSegment = locationChangeEvent.getLocation()
                .getFirstSegment();
        ModelMap model = getElement().getNode().getFeature(ModelMap.class);
        StateNode items = (StateNode) model.getValue("items");
        ModelList list = items.getFeature(ModelList.class);

        for (int i = 0; i < list.size(); i++) {
            StateNode itemNode = list.get(i);
            ModelMap map = itemNode.getFeature(ModelMap.class);

            String itemHref = ((String) map.getValue("href")).split("/")[0];
            boolean active = itemHref.equals(firstSegment);
            map.setValue("active", active);
        }
    }
}

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

import com.vaadin.annotations.StyleSheet;
import com.vaadin.hummingbird.StateNode;
import com.vaadin.hummingbird.nodefeature.ModelList;
import com.vaadin.hummingbird.nodefeature.ModelMap;
import com.vaadin.hummingbird.router.LocationChangeEvent;
import com.vaadin.hummingbird.router.View;
import com.vaadin.hummingbird.template.model.TemplateModel;
import com.vaadin.ui.Template;

/**
 * Layout showing the main menu above a sub view.
 *
 * @since
 * @author Vaadin Ltd
 */
@StyleSheet("css/site.css")
public final class MainLayout extends Template implements View {

    public static class MenuItem {
        private String href;
        private String caption;
        private boolean active = false;

        public MenuItem(String href, String caption) {
            this.href = href;
            this.caption = caption;
        }

        public String getHref() {
            return href;
        }

        public String getCaption() {
            return caption;
        }

        public boolean isActive() {
            return active;
        }
    }

    public interface MainLayoutModel extends TemplateModel {
        public void setItems(List<MenuItem> items);
    }

    /**
     * Creates a new layout and menu.
     */
    public MainLayout() {
        createMenu();
    }

    @Override
    protected MainLayoutModel getModel() {
        return (MainLayoutModel) super.getModel();
    }

    private void createMenu() {
        getModel().setItems(createMenuItems());
    }

    private static List<MenuItem> createMenuItems() {
        List<MenuItem> itemsList = new ArrayList<>();

        itemsList.add(new MenuItem("about", "About"));
        itemsList.add(new MenuItem("param/1", "Parameter view"));
        itemsList.add(new MenuItem("resource/", "Resource view"));
        itemsList.add(new MenuItem("dynresource", "Dynamic resource view"));

        return itemsList;
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        // The first segment uniquely identifies the views in this app so we do
        // not need to check anything else
        String navigatedToFirstSegment = locationChangeEvent.getLocation()
                .getFirstSegment();

        // There's not yet any convenient way of updating values in a model
        // list, so must use low-level APIs for that
        ModelMap model = getElement().getNode().getFeature(ModelMap.class);
        StateNode items = (StateNode) model.getValue("items");
        ModelList list = items.getFeature(ModelList.class);

        for (int i = 0; i < list.size(); i++) {
            StateNode itemNode = list.get(i);
            ModelMap map = itemNode.getFeature(ModelMap.class);

            String itemPathFirstSegment = ((String) map.getValue("href"))
                    .split("/")[0];
            boolean active = itemPathFirstSegment
                    .equals(navigatedToFirstSegment);
            map.setValue("active", active);
        }
    }
}

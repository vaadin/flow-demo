/*
 * Copyright 2000-2017 Vaadin Ltd.
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
package com.vaadin.flow.demo.website;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.demo.website.MainLayout.MainLayoutModel;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * Layout showing the main menu above a sub view.
 *
 * @since
 * @author Vaadin Ltd
 */
@HtmlImport("frontend://src/MainLayout.html")
@Tag("main-layout")
public final class MainLayout extends PolymerTemplate<MainLayoutModel>
        implements RouterLayout, AfterNavigationObserver, HasComponents {

    public static class MenuItem {
        private String href;
        private String caption;
        private boolean active = false;

        public MenuItem() {
            // no arg CTOR to be able to proxy
        }

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

        public void setActive(boolean active) {
            this.active = active;
        }
    }

    public interface MainLayoutModel extends TemplateModel {
        void setItems(List<MenuItem> items);

        List<MenuItem> getItems();
    }

    /**
     * Creates a new layout and menu.
     */
    public MainLayout() {
        createMenu();
    }

    @Override
    protected MainLayoutModel getModel() {
        return super.getModel();
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

    private void updateActive(MenuItem item, String firstSegment) {
        String itemPathFirstSegment = item.getHref().split("/")[0];
        item.setActive(itemPathFirstSegment.equals(firstSegment));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        // The first segment uniquely identifies the views in this app so we do
        // not need to check anything else
        String navigatedToFirstSegment = event.getLocation().getFirstSegment();

        getModel().getItems().stream()
                .forEach(item -> updateActive(item, navigatedToFirstSegment));
        
    }
}

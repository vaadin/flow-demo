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

import com.vaadin.hummingbird.RouterLink;
import com.vaadin.hummingbird.html.Div;
import com.vaadin.hummingbird.router.HasChildView;
import com.vaadin.hummingbird.router.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

/**
 * Layout showing the main menu above a sub view.
 *
 * @since
 * @author Vaadin Ltd
 */
public final class MainLayout extends SimpleView implements HasChildView {

    private final Div contentHolder = new Div();

    /**
     * Creates a new layout.
     */
    public MainLayout() {
        UI.getCurrent().getPage().addStyleSheet("css/site.css");

        add(createMenu(), contentHolder);
    }

    private Component createMenu() {
        Div menu = new Div();
        menu.setClassName("menu");

        RouterLink homeLink = createMenuLink("", HomeView.class);
        Div logo = new Div();
        logo.setClassName("logo");
        homeLink.add(logo);
        menu.add(homeLink, createMenuLink("About", AboutView.class), //
                createMenuLink("Parameter view", ParameterView.class,
                        Integer.toString(1)), //
                createMenuLink("Resource view", ResourcesView.class), //
                createMenuLink("Dynamic resource view",
                        DynamicResourcesView.class) //
        );
        return menu;
    }

    private static RouterLink createMenuLink(String caption,
            Class<? extends View> viewClass, String... params) {
        RouterLink link = new RouterLink(caption, viewClass, params);
        link.setClassName("menu-item");
        return link;
    }

    @Override
    public void setChildView(View content) {
        contentHolder.removeAll();
        content.getElement().getComponent().ifPresent(contentHolder::add);
    }
}

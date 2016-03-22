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

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.router.HasChildView;
import com.vaadin.hummingbird.router.View;
import com.vaadin.ui.UI;

/**
 * Layout showing the main menu above a sub view.
 *
 * @since
 * @author Vaadin Ltd
 */
public class MainLayout extends SimpleView implements HasChildView {

    private final Element contentHolder = new Element("div");

    /**
     * Creates a new layout.
     */
    public MainLayout() {
        super(new Element("div"));

        UI.getCurrent().getPage().addStyleSheet("css/site.css");

        Element menu = createMenu();
        getElement().appendChild(menu, contentHolder);

        // Placeholder content
        contentHolder.appendChild(new Element("div"));
    }

    private Element createMenu() {
        Element menu = new Element("div");
        menu.getClassList().add("menu");

        Element homeLink = createMenuLink("", "");
        Element logo = new Element("div");
        logo.getClassList().add("logo");
        homeLink.appendChild(logo);
        menu.appendChild(homeLink, createMenuLink("About", "about"), //
                createMenuLink("Parameter view", "param/1"), //
                createMenuLink("Resource view", "resource/") //
        );
        return menu;
    }

    private static Element createMenuLink(String caption, String path) {
        Element link = ElementFactory.createRouterLink(path, caption);
        link.getClassList().add("menu-item");
        return link;
    }

    @Override
    public void setChildView(View content) {
        Element contentElement = content.getElement();
        contentHolder.setChild(0, contentElement);
    }
}

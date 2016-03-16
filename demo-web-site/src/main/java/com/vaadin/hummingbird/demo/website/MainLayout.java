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
import com.vaadin.hummingbird.router.HasChildView;
import com.vaadin.hummingbird.router.View;
import com.vaadin.shared.ApplicationConstants;
import com.vaadin.ui.UI;

/**
 * Layout showing the main menu above a sub view.
 *
 * @since
 * @author Vaadin Ltd
 */
public class MainLayout extends SimpleView implements HasChildView {

    private final Element contentHolder = new Element("div");

    public static final String ABOUT = "about";
    public static final String DYNAMIC = "dynamic";
    public static final String BLOGS = "blogs";

    /**
     * Creates a new layout.
     */
    public MainLayout() {
        super(new Element("div"));

        UI.getCurrent().getPage().addStyleSheet("VAADIN/main.css");

        UI.getCurrent().getPage().addStyleSheet("css/site.css");

        Element menu = createMenu();
        getElement().appendChild(menu, contentHolder);
        contentHolder.getClassList().add("content");

        // Placeholder content
        contentHolder.appendChild(new Element("div"));
    }

    private Element createMenu() {
        Element menu = new Element("div");
        menu.getClassList().add("menu");

        // Configuring menu based on router configuration added in a separate PR
        Element homeLink = createMenuLink("", "");
        Element logo = new Element("div");
        logo.getClassList().add("logo");
        homeLink.appendChild(logo);
        menu.appendChild(createMenuLink("Home", ""),
                createMenuLink("About", ABOUT),
                createMenuLink("Dynamic 1", DYNAMIC + "/one"),
                createMenuLink("Dynamic 2", DYNAMIC + "/two"),
                createMenuLink("Blogs", BLOGS));
        return menu;
    }

    private static Element createMenuLink(String caption, String path) {
        Element link = new Element("a");

        link.setTextContent(caption);
        link.setAttribute("href", path);
        link.getClassList().add("menu-item");
        link.setAttribute(ApplicationConstants.ROUTER_LINK_ATTRIBUTE, "");

        return link;
    }

    @Override
    public void setChildView(View content) {
        Element contentElement = content.getElement();

        contentHolder.setChild(0, contentElement);
    }
}

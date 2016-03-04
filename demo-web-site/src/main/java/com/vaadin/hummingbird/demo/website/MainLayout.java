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

import java.util.concurrent.atomic.AtomicInteger;

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.router.HasSubView;
import com.vaadin.hummingbird.router.Location;
import com.vaadin.hummingbird.router.RouterUI;
import com.vaadin.hummingbird.router.View;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

public class MainLayout extends SimpleView implements HasSubView {

    private final Element contentHolder = new Element("div");

    // Counter used to show that layout instances are reused
    private static final AtomicInteger instanceCounter = new AtomicInteger();

    public MainLayout() {
        super(new Element("div"));

        Element menu = createMenu();

        getElement().appendChild(
                Element.createText("My site, layout instance "
                        + instanceCounter.incrementAndGet()),
                menu, contentHolder);
    }

    private Element createMenu() {
        Element menu = new Element("div");

        // Configuring menu based on router configuration added in a separate PR
        menu.appendChild(createMenuLink("Home", ""),
                createMenuLink("About", "about"),
                createMenuLink("Dynamic 1", "dynamic/one"),
                createMenuLink("Dynamic 2", "dynamic/two"));
        return menu;
    }

    private static Element createMenuLink(String caption, String path) {
        Element link = new Element("a");

        link.setTextContent(caption);
        link.setAttribute("href", path);

        // All of this should be handled by the framework based on a magical
        // attribute
        link.addEventListener("click", e -> {
            VaadinService.getCurrent().getRouter()
                    .navigate((RouterUI) UI.getCurrent(), new Location(path));
        }, "history.pushState(null,'', element.getAttribute('href'))",
                "event.preventDefault()");

        return link;
    }

    @Override
    public void setSubView(View content) {
        Element contentElement = content.getElement();

        // state tree doesn't yet optimize re-adding
        if (contentHolder.getChildCount() == 1
                && contentHolder.getChild(0).equals(contentElement)) {
            return;
        }

        contentHolder.removeAllChildren();
        contentHolder.appendChild(contentElement);
    }
}

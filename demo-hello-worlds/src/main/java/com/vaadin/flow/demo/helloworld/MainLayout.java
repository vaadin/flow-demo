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
package com.vaadin.flow.demo.helloworld;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.router.RouterLayout;
import com.vaadin.router.RouterLink;
import com.vaadin.router.PageTitle;
import com.vaadin.router.event.AfterNavigationEvent;
import com.vaadin.router.event.AfterNavigationListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Text;
import com.vaadin.ui.common.HasElement;
import com.vaadin.ui.common.HtmlContainer;
import com.vaadin.ui.common.StyleSheet;
import com.vaadin.ui.html.Div;
import com.vaadin.ui.html.H1;
import com.vaadin.ui.html.H2;

/**
 * The main view contains a menu and a container for either of the hello world
 * variants.
 */
@StyleSheet("frontend://src/styles.css")
public class MainLayout extends Composite<Div>
        implements RouterLayout, AfterNavigationListener {

    private Map<String, RouterLink> targetPaths = new HashMap<>();

    private Div container;

    /**
     * Main layout constructor.
     */
    public MainLayout() {
        // Guiding texts
        H1 heading = new H1("3 ways to say Hello");
        Text intro = new Text(
                "Three different ways of implementing Hello World using Vaadin Flow");
        Div menu = buildMenu();

        // Set up the container where sub views will be shown
        container = new Div();
        container.addClassName("container");

        getContent().addClassName("main-view");
        getContent().add(heading, intro, menu, container);
    }

    private Div buildMenu() {
        // Create links to each of the different sub views
        RouterLink template = new RouterLink("Template",
                HelloWorldTemplate.class);
        template.setId("template-link");

        RouterLink components = new RouterLink("Component",
                HelloWorldComponent.class);
        components.setId("components-link");

        RouterLink elements = new RouterLink("Element",
                HelloWorldElement.class);
        elements.setId("elements-link");

        // Add menu links to a map for selection handling.
        targetPaths.put(template.getHref(), template);
        targetPaths.put(components.getHref(), components);
        targetPaths.put(elements.getHref(), elements);

        HtmlContainer ul = new HtmlContainer("ul");
        ul.setClassName("topnav");
        ul.add(template, components, elements);

        Div menu = new Div();
        menu.setClassName("menu");

        menu.add(ul);
        return menu;
    }

    @Override
    public void setRouterLayoutContent(HasElement child) {
        // Update what we show whenever the sub view changes
        container.removeAll();

        if (child != null) {
            container.add(new H2(
                    child.getClass().getAnnotation(PageTitle.class).value()));
            container.add((Component) child);
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        targetPaths.values().forEach(link -> link.removeClassName("selected"));

        // We just use the location path as we don't have nested paths.
        if (!event.getLocation().getPath().isEmpty()) {
            targetPaths.get(event.getLocation().getPath())
                    .addClassName("selected");
        }
    }
}

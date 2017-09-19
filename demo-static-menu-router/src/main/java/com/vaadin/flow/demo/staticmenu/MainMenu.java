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
package com.vaadin.flow.demo.staticmenu;

import java.util.Optional;

import com.vaadin.flow.demo.staticmenu.download.DownloadView;
import com.vaadin.flow.demo.staticmenu.elements.ElementsView;
import com.vaadin.flow.demo.staticmenu.framework.FrameworkView;
import com.vaadin.flow.html.Anchor;
import com.vaadin.flow.html.Div;
import com.vaadin.flow.html.HtmlContainer;
import com.vaadin.flow.router.event.AfterNavigationEvent;
import com.vaadin.flow.router.event.AfterNavigationListener;

/**
 * Main menu bar containing top level navigation items.
 *
 * @author Vaadin
 */
public class MainMenu extends MainMenuBar implements AfterNavigationListener {

    @Override
    public void init() {
        initHomeLink();
        initLinkContainer();
    }

    private void initHomeLink() {
        Anchor homeLink = new Anchor("/", "");
        homeLink.getElement().setAttribute("router-link", "true");
        Div logo = new Div();
        logo.setClassName("logo");
        homeLink.add(logo);
        add(homeLink);
    }

    private void initLinkContainer() {
        HtmlContainer ul = new HtmlContainer("ul");
        ul.setClassName("topnav");
        add(ul);

        ul.add(createLink(FrameworkView.class, "Framework"));
        ul.add(createLink(ElementsView.class, "Elements"));
        ul.add(createLink(DownloadView.class, "Download"));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        if (event.getLocation().getPath().isEmpty()) {
            clearSelection();
        } else {
            StringBuilder path = new StringBuilder();
            for (String segment : event.getLocation().getSegments()) {
                path.append(segment);
                Optional<Class> target = getTargetForPath(path.toString());
                if (target.isPresent()) {
                    clearSelection();
                    activateMenuTarget(target.get());
                    break;
                }
                path.append("/");
            }
        }
    }
}

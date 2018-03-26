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

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.demo.staticmenu.community.blog.BlogList;
import com.vaadin.flow.demo.staticmenu.download.DownloadView;
import com.vaadin.flow.demo.staticmenu.elements.ElementsView;
import com.vaadin.flow.demo.staticmenu.framework.FrameworkView;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;

/**
 * Main menu bar containing top level navigation items.
 *
 * @author Vaadin
 */
public class MainMenu extends MainMenuBar implements AfterNavigationObserver {

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
        HorizontalLayout layout = new HorizontalLayout();
        layout.setClassName("topnav");
        add(layout);

        layout.add(createLink(FrameworkView.class));
        layout.add(createLink(ElementsView.class));
        layout.add(createLink(DownloadView.class));
        layout.add(createLink(BlogList.class));

        layout.add(getLocales());

        layout.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER,
                layout.getChildren().collect(Collectors.toList())
                        .toArray(new Component[] {}));
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

    private Component[] getLocales() {
        Button english = new Button("EN", event -> setLocale(Lang.LOCALE_EN));
        Button finnish = new Button("FI", event -> setLocale(Lang.LOCALE_FI));
        Button japanese = new Button("JA", event -> setLocale(Lang.LOCALE_JA));

        return new Component[] { english, finnish, japanese };
    }

    private void setLocale(Locale locale) {
        UI.getCurrent().setLocale(locale);
    }
}

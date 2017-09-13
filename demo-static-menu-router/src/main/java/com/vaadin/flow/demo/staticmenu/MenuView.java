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

import com.vaadin.annotations.Route;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.flow.html.Anchor;
import com.vaadin.flow.html.Div;
import com.vaadin.flow.html.HtmlContainer;
import com.vaadin.ui.Component;

/**
 * The main menu.
 */
@Route("")
@StyleSheet("css/site.css")
public class MenuView extends Div {

    private HtmlContainer ul;
    private Anchor homeLink;

    public MenuView() {
        setClassName("menu");
        initHomeLink();
        initLinkContainer();
    }

    private void initHomeLink() {
        homeLink = new Anchor("home", "");
        Div logo = new Div();
        logo.setClassName("logo");
        homeLink.add(logo);
        add(homeLink);
    }

    private void initLinkContainer() {
        ul = new HtmlContainer("ul");
        ul.setClassName("topnav");
        add(ul);
        addLink(HomeView.class);
    }

    private void addLink(Class<? extends Component> navigationTarget) {
        Anchor link = new Anchor(Util.getNavigationTargetPath(navigationTarget),
                Util.getNavigationTargetName(navigationTarget));
        ul.add(link);
    }
}
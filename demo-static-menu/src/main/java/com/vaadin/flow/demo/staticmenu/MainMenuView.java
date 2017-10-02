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

import java.util.stream.Stream;

import com.vaadin.ui.common.StyleSheet;
import com.vaadin.flow.demo.staticmenu.community.CommunityView;
import com.vaadin.flow.demo.staticmenu.download.DownloadView;
import com.vaadin.flow.demo.staticmenu.elements.ElementsView;
import com.vaadin.flow.demo.staticmenu.framework.FrameworkView;
import com.vaadin.ui.html.Div;
import com.vaadin.ui.common.HtmlContainer;
import com.vaadin.router.RouterLink;
import com.vaadin.flow.router.View;
import com.vaadin.ui.Component;

/**
 * The main menu.
 *
 * @author Vaadin
 * @since
 */
@StyleSheet("context://css/site.css")
public class MainMenuView extends SimpleMenuView {

    private HtmlContainer ul;
    private RouterLink homeLink;

    /**
     * Creates the view.
     */
    public MainMenuView() {
        getMenu().setClassName("menu");

        homeLink = new RouterLink("", HomeView.class);
        registerMenuLinkView(HomeView.class);
        Div logo = new Div();
        logo.setClassName("logo");
        homeLink.add(logo);

        ul = new HtmlContainer("ul");
        ul.setClassName("topnav");

        addItem(FrameworkView.class);
        addItem(ElementsView.class);
        addItem("Pro Tools", (Class<? extends View>) null);
        addItem(DownloadView.class);
        addItem(CommunityView.class);
        addItem("Services", (Class<? extends View>) null);

        getMenu().add(homeLink, ul);
    }

    @Override
    protected void addMenuElement(Component component) {
        ul.add(component);
    }

    @Override
    protected Stream<Component> getMenuElements() {
        return Stream.concat(Stream.of(homeLink), ul.getChildren());
    }
}

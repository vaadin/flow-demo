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

import java.util.stream.Stream;

import com.vaadin.hummingbird.demo.website.community.CommunityView;
import com.vaadin.hummingbird.demo.website.download.DownloadView;
import com.vaadin.hummingbird.demo.website.elements.ElementsView;
import com.vaadin.hummingbird.demo.website.framework.FrameworkView;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.router.View;
import com.vaadin.ui.UI;

public class MainMenuView extends SimpleMenuView {

    private Element ul;
    private Element homeLink;

    public MainMenuView() {
        UI.getCurrent().getPage().addStyleSheet("css/site.css");

        getMenu().getClassList().add("menu");

        homeLink = ElementFactory.createRouterLink("", SiteRouterConfigurator
                .getNavigablePath(HomeView.class).orElse(null));
        registerMenuLinkView(HomeView.class);
        Element logo = ElementFactory.createDiv().setAttribute("class", "logo");
        homeLink.appendChild(logo);

        ul = new Element("ul").setAttribute("class", "topnav");

        addItem(FrameworkView.class);
        addItem(ElementsView.class);
        addItem("Pro Tools", (Class<? extends View>) null);
        addItem(DownloadView.class);
        addItem(CommunityView.class);
        addItem("Services", (Class<? extends View>) null);

        getMenu().appendChild(homeLink, ul);
    }

    @Override
    protected void addMenuElement(Element e) {
        ul.appendChild(e);
    }

    @Override
    protected Stream<Element> getMenuElements() {
        return Stream.concat(Stream.of(homeLink), ul.getChildren());
    }
}

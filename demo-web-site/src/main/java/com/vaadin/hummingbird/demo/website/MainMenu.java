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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.shared.ApplicationConstants;

public class MainMenu {

    private Element element;
    private Map<String, Element> menuItems = new HashMap<>();

    public MainMenu() {
        element = new Element("div");
        element.getClassList().add("menu");

        Element homeLink = createMenuLink("", "");
        Element logo = new Element("div").setAttribute("class", "logo");
        // logo.getClassList().add("logo");
        homeLink.appendChild(logo);

        Element ul = new Element("ul").setAttribute("class", "topnav");

        Element framework = createMenuLink("Framework", "framework");
        Element elements = createMenuLink("Elements", "elements");
        Element protools = createDisabledMenuLink("Pro Tools", "pro-tools");
        Element download = createMenuLink("Download", "download");
        Element community = createMenuLink("Community", "community");
        Element services = createDisabledMenuLink("Services",
                "tools-and-services");

        addMenuItem(homeLink, framework, elements, protools, download,
                community, services);

        ul.appendChild(framework, elements, protools, download, community,
                services);
        element.appendChild(homeLink, ul);

    }

    private void addMenuItem(Element... linkElements) {
        for (Element linkElement : linkElements) {
            menuItems.put(linkElement.getTextContent(), linkElement);
        }
    }

    private static Element createMenuLink(String caption, String path) {
        Element link = new Element("a");

        link.setTextContent(caption);
        link.setAttribute("href", path);
        link.setAttribute(ApplicationConstants.ROUTER_LINK_ATTRIBUTE, "");

        return link;
    }

    private static Element createDisabledMenuLink(String caption, String path) {
        Element link = new Element("span");
        link.setTextContent(caption);
        return link;
    }

    public Element getElement() {
        return element;
    }

    public void setSelected(String caption) {
        for (Entry<String, Element> entry : menuItems.entrySet()) {
            Util.setClassName(entry.getValue(), "selected",
                    caption.equals(entry.getKey()));
        }
    }

}

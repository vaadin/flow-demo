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
import com.vaadin.shared.ApplicationConstants;

public class Menu {

    private Element element;

    public Menu() {
        element = new Element("div");
        element.getClassList().add("menu");

        Element homeLink = createMenuLink("", "");
        Element logo = new Element("div").setAttribute("class", "logo");
        // logo.getClassList().add("logo");
        homeLink.appendChild(logo);

        Element framework = createMenuLink("Framework", "/framework");
        Element elements = createMenuLink("Elements", "/elements");
        Element protools = createMenuLink("Pro Tools", "/pro-tools");
        Element download = createMenuLink("Download", "/download");
        Element community = createMenuLink("Community", "/community");
        Element services = createMenuLink("Services", "/tools-and-services");

        element.appendChild(framework, elements, protools, download, community,
                services);

    }

    private static Element createMenuLink(String caption, String path) {
        Element link = new Element("a");

        link.setTextContent(caption);
        link.setAttribute("href", path);
        link.setAttribute(ApplicationConstants.ROUTER_LINK_ATTRIBUTE, "");

        return link;
    }

    public Element getElement() {
        return element;
    }
}

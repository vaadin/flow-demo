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

import java.util.function.Predicate;

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.router.HasChildView;
import com.vaadin.hummingbird.router.Router;
import com.vaadin.hummingbird.router.RouterConfiguration;
import com.vaadin.hummingbird.router.RouterUI;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

public class Util {

    public static Element createRouterLink(String caption, String href) {
        if (href == null) {
            return new Element("span").setTextContent(caption);
        } else {
            return new Element("a").setAttribute("href", href)
                    .setAttribute("routerLink", "").setTextContent(caption);
        }
    }

    public static void setClassName(Element element, String className,
            boolean add) {
        if (add) {
            element.getClassList().add(className);
        } else {
            element.getClassList().remove(className);
        }
    }

    public static Router getRouter() {
        return VaadinService.getCurrentRequest().getService().getRouter();
    }

    public static RouterUI getUI() {
        return (RouterUI) UI.getCurrent();
    }

    public static RouterConfiguration getRouterConfiguration() {
        return getRouter().getConfiguration();
    }

    public static Predicate<? super Class<? extends HasChildView>> isSame(
            Object o) {
        return other -> other == o;
    }

}

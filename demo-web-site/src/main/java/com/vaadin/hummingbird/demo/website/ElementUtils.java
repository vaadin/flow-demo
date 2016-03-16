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

/**
 * @author Vaadin Ltd
 *
 */
public interface ElementUtils {

    static Element createDiv() {
        return new Element("div");
    }

    static Element createAnchor() {
        return new Element("a");
    }

    static Element createAnchor(String href) {
        Element element = createAnchor();
        setHref(element, href);
        return element;
    }

    static void setHref(Element element, String value) {
        element.setAttribute("href", value);
    }
}
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
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.router.View;

/**
 * Abstract view class using the same element all the time.
 *
 * @since
 * @author Vaadin Ltd
 */
public abstract class SimpleView implements View {

    private final Element element;

    /**
     * Creates a new simple view for the given element.
     *
     * @param element
     *            the element of the view
     */
    public SimpleView(Element element) {
        this.element = element;
    }

    @Override
    public final Element getElement() {
        return element;
    }

    protected final Element getMappingInfo(String mappingInfo) {
        Element mapping = ElementFactory.createDiv();
        Element textElement = ElementFactory
                .createStrong("Mapped using \"" + mappingInfo + "\"");
        textElement.getStyle().set("borderBottom", "1px solid black");
        mapping.getStyle().set("marginBottom", "1em");

        mapping.appendChild(textElement);
        return mapping;

    }

}

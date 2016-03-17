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
package com.vaadin.hummingbird.demo.staticmenu;

import java.util.stream.Stream;

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.router.View;

/**
 * A menu which adds all items to a single div.
 *
 * @author Vaadin
 * @since
 */
public class SimpleMenuView extends MenuView {

    private Element element;
    private Element menu;

    /**
     * Creates the view.
     */
    public SimpleMenuView() {
        element = ElementFactory.createDiv();
        menu = ElementFactory.createDiv();

        element.appendChild(menu);
        element.appendChild(ElementFactory.createDiv());
    }

    @Override
    public Element getElement() {
        return element;
    }

    protected final Element getMenu() {
        return menu;
    }

    @Override
    protected void addMenuElement(Element e) {
        getMenu().appendChild(e);
    }

    @Override
    protected Stream<Element> getMenuElements() {
        return getMenu().getChildren();
    }

    @Override
    public void setChildView(View childView) {
        getElement().setChild(1, childView.getElement());
    }

}

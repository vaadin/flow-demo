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

import com.vaadin.hummingbird.html.Div;
import com.vaadin.hummingbird.html.HtmlComponent;
import com.vaadin.hummingbird.router.View;
import com.vaadin.ui.Component;

/**
 * A menu which adds all items to a single div.
 *
 * @author Vaadin
 * @since
 */
public class SimpleMenuView extends MenuView {

    private Div menu;

    /**
     * Creates the view.
     */
    public SimpleMenuView() {
        menu = new Div();

        add(menu, new Div());
    }

    protected final Div getMenu() {
        return menu;
    }

    @Override
    protected void addMenuElement(HtmlComponent component) {
        getMenu().add(component);
    }

    @Override
    protected Stream<HtmlComponent> getMenuElements() {
        return getMenu().getChildren().map(HtmlComponent.class::cast);
    }

    @Override
    public void setChildView(View childView) {
        getElement().setChild(1, childView.getElement());
    }

    @Override
    public final void add(Component... components) {
        super.add(components);
    }

}

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

import com.vaadin.flow.html.Div;
import com.vaadin.flow.router.View;
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
    protected void addMenuElement(Component component) {
        getMenu().add(component);
    }

    @Override
    protected Stream<Component> getMenuElements() {
        return getMenu().getChildren();
    }

    @Override
    public void setChildView(View childView) {
        getElement().setChild(1, childView.getElement());
    }

}

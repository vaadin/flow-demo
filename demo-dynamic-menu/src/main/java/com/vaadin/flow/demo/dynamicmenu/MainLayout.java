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
package com.vaadin.flow.demo.dynamicmenu;

import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.LocationChangeEvent;
import com.vaadin.flow.router.View;
import com.vaadin.router.RouterLayout;
import com.vaadin.router.event.BeforeEnterObserver;
import com.vaadin.router.event.BeforeNavigationEvent;
import com.vaadin.ui.common.StyleSheet;
import com.vaadin.ui.html.Div;

/**
 * Layout showing the main menu above a sub view.
 *
 * @since
 * @author Vaadin Ltd
 */
@StyleSheet("css/site.css")
public final class MainLayout extends Div implements RouterLayout {

    private final Div contentHolder = new Div();
    private final Menu menu;

    /**
     * Creates a new layout.
     */
    public MainLayout() {
        setClassName("main");

        menu = new Menu();
        add(menu, contentHolder);
        contentHolder.setClassName("content");
    }
}

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

import com.vaadin.ui.common.StyleSheet;
import com.vaadin.ui.html.Div;
import com.vaadin.router.RouterLayout;

/**
 * Application main layout containing everything.
 *
 * @author Vaadin
 */
@StyleSheet("frontend://src/site.css")
public class MainLayout extends Div implements RouterLayout {

    private MainMenuBar menu = new MainMenu();

    /**
     * Setup main layout.
     */
    public MainLayout() {
        add(menu);
    }
}

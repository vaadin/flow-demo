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
package com.vaadin.hummingbird.demo.dynamicmenu;

import com.vaadin.annotations.Title;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.router.View;
import com.vaadin.ui.Component;

/**
 * The static home page.
 *
 * @since
 * @author Vaadin Ltd
 */
@Title("Home")
public class HomeView extends Component implements View {
    /**
     * Creates a new home view.
     */
    public HomeView() {
        super(ElementFactory
                .createParagraph("Please select a category in the menu"));
    }
}

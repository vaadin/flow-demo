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

import com.vaadin.flow.router.View;
import com.vaadin.router.PageTitle;
import com.vaadin.ui.common.HtmlContainer;
import com.vaadin.ui.Tag;

/**
 * The static home page.
 *
 * @since
 * @author Vaadin Ltd
 */
@PageTitle("Home")
@Tag("p")
public class HomeView extends HtmlContainer implements View {
    /**
     * Creates a new home view.
     */
    public HomeView() {
        setText("Please select a category in the menu");
    }
}

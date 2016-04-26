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
import com.vaadin.hummingbird.router.HasChildView;
import com.vaadin.hummingbird.router.View;
import com.vaadin.ui.Template;
import com.vaadin.ui.UI;

/**
 * Layout showing the main menu above a sub view.
 *
 * @since
 * @author Vaadin Ltd
 */
public final class MainLayout extends Template implements View, HasChildView {

    private final Element contentHolder;

    /**
     * Creates a new layout.
     */
    public MainLayout() {
        UI.getCurrent().getPage().addStyleSheet("css/site.css");
        contentHolder = getElement().getChild(1);

    }

    @Override
    public void setChildView(View content) {
        contentHolder.removeAllChildren();
        if (content != null) {
            contentHolder.appendChild(content.getElement());
        }
    }
}

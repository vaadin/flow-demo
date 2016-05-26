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

import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.html.HtmlContainer;
import com.vaadin.hummingbird.router.RouterLink;
import com.vaadin.hummingbird.router.View;

/**
 * A menu item component.
 *
 * @author Vaadin Ltd
 *
 */
@Tag("li")
public class MenuItemComponent extends HtmlContainer {

    private static final String CLASS_SELECTED = "selected";

    /**
     * Creates a new item component.
     *
     * @param viewClass
     *            view class for the item's link to navigate
     * @param id
     *            category id
     * @param name
     *            category name
     */
    public MenuItemComponent(Class<? extends View> viewClass, int id,
            String name) {
        RouterLink categoryLink = new RouterLink(name, viewClass,
                Integer.toString(id));
        add(categoryLink);
    }

    /**
     * Selects or deselects the item.
     *
     * @param select
     *            if {@code true} then select the item and deselect it otherwise
     */
    public void select(boolean select) {
        if (select) {
            getElement().getChild(0).getClassList().add(CLASS_SELECTED);
        } else {
            getElement().getChild(0).getClassList().remove(CLASS_SELECTED);
        }
    }

}

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
package com.vaadin.hummingbird.demo.staticmenu.download;

import com.vaadin.hummingbird.demo.staticmenu.SimpleMenuView;
import com.vaadin.hummingbird.router.View;

/**
 * The Download menu.
 *
 * @since
 * @author Vaadin Ltd
 */
public class DownloadMenuView extends SimpleMenuView {

    /**
     * Creates the view.
     */
    public DownloadMenuView() {
        super();

        addItem(DocsView.class);
        addItem("Vaadin Icons", IconsAboutView.class);

        getMenu().setAttribute("class", "submenu");
    }

    @Override
    protected void markMenuLinkSelected(Class<? extends View> childViewClass) {
        if (childViewClass == DocsSubView.class) {
            super.markMenuLinkSelected(DocsView.class);
        } else {
            super.markMenuLinkSelected(childViewClass);
        }

    }
}

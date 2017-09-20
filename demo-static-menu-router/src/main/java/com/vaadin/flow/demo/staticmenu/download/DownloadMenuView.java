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
package com.vaadin.flow.demo.staticmenu.download;

import com.vaadin.router.ParentLayout;
import com.vaadin.router.RoutePrefix;
import com.vaadin.flow.demo.staticmenu.MainLayout;
import com.vaadin.flow.demo.staticmenu.SimpleMenuBar;
import com.vaadin.router.RouterLayout;

/**
 * The Download menu.
 *
 * @author Vaadin Ltd
 */
@RoutePrefix("download")
@ParentLayout(MainLayout.class)
public class DownloadMenuView extends SimpleMenuBar implements RouterLayout {

    /**
     * Creates the view.
     */
    public DownloadMenuView() {
        addMenuElement(DocsView.class, "Docs");
        addMenuElement(IconsView.class, "Vaadin Icons");
    }
}

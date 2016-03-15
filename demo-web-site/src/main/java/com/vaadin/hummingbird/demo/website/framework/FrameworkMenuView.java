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
package com.vaadin.hummingbird.demo.website.framework;

import com.vaadin.hummingbird.demo.website.SimpleMenuView;
import com.vaadin.hummingbird.demo.website.download.DocsView;

public class FrameworkMenuView extends SimpleMenuView {

    public FrameworkMenuView() {
        super();

        getMenu().setAttribute("class", "submenu");

        addItem("Demo", DemoView.class);
        addItem("Tutorial", TutorialView.class);
        addItem("Docs", DocsView.class);
        addItem("Add-ons", DirectoryView.class);
    }

}

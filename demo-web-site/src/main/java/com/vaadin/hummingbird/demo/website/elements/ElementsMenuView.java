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
package com.vaadin.hummingbird.demo.website.elements;

import com.vaadin.hummingbird.demo.website.SimpleMenuView;
import com.vaadin.hummingbird.demo.website.community.ForumView;
import com.vaadin.hummingbird.demo.website.download.DocsView;;

public class ElementsMenuView extends SimpleMenuView {

    public ElementsMenuView() {
        super();
        getMenu().setAttribute("class", "submenu");

        addItem("Forum", ForumView.class);
        addItem("Getting Started",
                "docs/-/part/elements/elements-getting-started.html");
        addItem("Videos", VideosView.class);
        addItem("Demos", ElementsView.class);// FIXME #demos
        addItem("Docs", DocsView.class);
    }

}

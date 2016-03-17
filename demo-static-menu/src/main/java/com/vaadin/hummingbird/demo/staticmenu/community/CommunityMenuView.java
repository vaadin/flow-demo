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
package com.vaadin.hummingbird.demo.staticmenu.community;

import com.vaadin.hummingbird.demo.staticmenu.SimpleMenuView;
import com.vaadin.hummingbird.demo.staticmenu.community.blog.BlogPost;
import com.vaadin.hummingbird.demo.staticmenu.framework.DirectoryView;

/**
 * The community menu.
 *
 * @author Vaadin
 * @since
 */
public class CommunityMenuView extends SimpleMenuView {

    /**
     * Creates the view.
     */
    public CommunityMenuView() {
        super();
        getMenu().setAttribute("class", "submenu");

        addItem(ForumView.class);
        addItem("Blog", BlogPost.class, "id", "");
        addItem(WikiView.class);
        addItem(MeetupView.class);
        addItem(ContributeView.class);
        addItem(CloudView.class);
        addItem(WebinarsView.class);
        addItem("Add-ons", DirectoryView.class);
    }

}

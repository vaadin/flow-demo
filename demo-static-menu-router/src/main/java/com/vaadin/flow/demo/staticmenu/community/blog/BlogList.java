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
package com.vaadin.flow.demo.staticmenu.community.blog;

import com.vaadin.flow.demo.staticmenu.MainLayout;
import com.vaadin.flow.demo.staticmenu.community.blog.backend.BlogRecord;
import com.vaadin.flow.demo.staticmenu.community.blog.backend.BlogsService;
import com.vaadin.router.Route;
import com.vaadin.router.Router;
import com.vaadin.router.Title;
import com.vaadin.ui.UI;
import com.vaadin.ui.html.Anchor;
import com.vaadin.ui.html.Div;

/**
 * The blog post view.
 *
 * @author Vaadin Ltd
 */
@Route(value = "blog", layout = MainLayout.class)
@Title("Blog Post")
public class BlogList extends Div {

    public BlogList() {
        BlogsService.getInstance().getItems()
                .forEach(blogRecord -> addRecord(blogRecord));
    }

    private void addRecord(BlogRecord record) {
        String url = ((Router) UI.getCurrent().getRouter().get())
                .getUrl(BlogPost.class, record.getId());
        Anchor link = new Anchor(url, record.getTitle());
        link.getElement().setAttribute("router-link", "true");

        add(new Div(link));
    }
}

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

import java.util.Collection;

import com.vaadin.flow.demo.staticmenu.community.blog.backend.BlogRecord;
import com.vaadin.flow.demo.staticmenu.community.blog.backend.BlogsService;
import com.vaadin.ui.html.Div;
import com.vaadin.ui.common.HtmlContainer;
import com.vaadin.flow.router.LocationChangeEvent;
import com.vaadin.flow.router.View;

/**
 * The blog post view.
 *
 * @since
 * @author Vaadin Ltd
 */
public class BlogPost extends Div implements View {

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        removeAll();

        Collection<BlogRecord> items = BlogsService.getInstance().getItems();
        if (items.isEmpty()) {
            return;
        }

        BlogRecord record = items.iterator().next();
        Long id = getId(locationChangeEvent.getPathParameter("id"));
        if (id != null) {
            record = BlogsService.getInstance().getRecord(id).orElse(null);
        }

        if (record == null) {
            locationChangeEvent.rerouteToErrorView();
            return;
        } else {
            HtmlContainer title = new HtmlContainer("h1");
            title.setText(record.getTitle());
            title.setClassName("blog-item-title");

            Div text = new Div();
            text.setText(record.getText());
            text.setClassName("blog-content");

            add(title, text);
        }
    }

    private Long getId(String item) {
        try {
            return Long.parseLong(item);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}

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
package com.vaadin.hummingbird.demo.staticmenu.community.blog;

import java.util.Collection;

import com.vaadin.hummingbird.demo.staticmenu.SimpleView;
import com.vaadin.hummingbird.demo.staticmenu.community.blog.backend.BlogRecord;
import com.vaadin.hummingbird.demo.staticmenu.community.blog.backend.BlogsService;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.router.LocationChangeEvent;

/**
 * The blog post view.
 *
 * @since
 * @author Vaadin Ltd
 */
public class BlogPost extends SimpleView {

    /**
     * Creates the view.
     */
    public BlogPost() {
        super(ElementFactory.createDiv());
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        getElement().removeAllChildren();

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
            Element error = ElementFactory.createDiv("Unable to find the post");
            error.getClassList().add("no-post");
            getElement().appendChild(error);
        } else {
            Element title = ElementFactory.createHeading1(record.getTitle());
            title.getClassList().add("blog-item-title");

            Element text = ElementFactory.createDiv(record.getText());
            text.getClassList().add("blog-content");

            getElement().appendChild(title, text);
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

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
package com.vaadin.hummingbird.demo.website.blogs;

import java.util.Collection;

import com.vaadin.hummingbird.demo.website.ElementUtils;
import com.vaadin.hummingbird.demo.website.SimpleView;
import com.vaadin.hummingbird.demo.website.blogs.backend.BlogRecord;
import com.vaadin.hummingbird.demo.website.blogs.backend.BlogsService;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.router.Location;

public class BlogPost extends SimpleView {

    public BlogPost() {
        super(ElementUtils.createDiv());
    }

    @Override
    public void onLocationChange(Location location) {
        getElement().removeAllChildren();

        Collection<BlogRecord> items = BlogsService.getInstance().getItems();
        if (items.size() == 0) {
            return;
        }

        BlogRecord record = items.iterator().next();
        if (location.getSubLocation().hasSegments()) {
            Long id = getId(location.getSubLocation().getFirstSegment());
            if (id != null) {
                record = BlogsService.getInstance().getRecord(id).orElse(null);
            }
        }

        if (record == null) {
            Element error = ElementUtils.createDiv();
            error.setTextContent("Unable to find the post");
            error.getClassList().add("no-post");
            getElement().appendChild(error);
        } else {
            Element title = ElementUtils.createDiv();
            title.getClassList().add("blog-item-title");
            title.setTextContent(record.getTitle());

            Element text = ElementUtils.createDiv();
            text.getClassList().add("blog-content");
            text.setTextContent(record.getText());

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

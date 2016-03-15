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

import java.util.Optional;

import com.vaadin.hummingbird.demo.website.SimpleView;
import com.vaadin.hummingbird.demo.website.blogs.backend.BlogRecord;
import com.vaadin.hummingbird.demo.website.blogs.backend.BlogsService;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.router.Location;
import com.vaadin.ui.UI;

public class BlogsView extends SimpleView {

    private final long topBlogId;

    public BlogsView() {
        super(new Element(BlogsList.DIV));

        UI.getCurrent().getPage().addStyleSheet("VAADIN/blogs.css");

        BlogsList list = new BlogsList();
        getElement().appendChild(list.getElement());
        topBlogId = list.getTopBlogId();
    }

    @Override
    public void onLocationChange(Location location) {
        Element blog = null;
        if (location.hasSegments()) {
            String item = location.getSubLocation().getFirstSegment();
            Long id = getId(item);
            blog = makePostElement(id == null ? topBlogId : id);
        }
        setPost(blog);
    }

    private Long getId(String item) {
        try {
            return Long.parseLong(item);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void setPost(Element blogElement) {
        Element blog = blogElement;
        if (blog == null) {
            blog = new Element(BlogsList.DIV);
        }
        if (getElement().getChildCount() > 1) {
            getElement().removeChild(1);
        }
        getElement().appendChild(blog);
    }

    private Element makePostElement(long id) {
        Element blog = null;
        Optional<BlogRecord> record = BlogsService.getInstance().getRecord(id);
        if (record.isPresent()) {
            BlogPost post = new BlogPost(record.get());
            blog = post.getElement();
        }
        return blog;
    }
}

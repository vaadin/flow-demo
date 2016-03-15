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

import java.time.format.DateTimeFormatter;
import java.util.Collection;

import com.vaadin.hummingbird.demo.website.MainLayout;
import com.vaadin.hummingbird.demo.website.blogs.backend.BlogItem;
import com.vaadin.hummingbird.demo.website.blogs.backend.BlogsService;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.shared.ApplicationConstants;

public class BlogsList {

    static final String TITLE_STYLE = "blog-item-title";

    static final String DIV = "div";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yy hh:mm a");

    private static final String HREF = "href";

    private final Element container;

    private static final String A = "a";

    private final long firstBlogId;

    public BlogsList() {
        container = new Element(DIV);
        container.getClassList().add("blog-list");
        firstBlogId = init();
    }

    public Element getElement() {
        return container;
    }

    public long getTopBlogId() {
        return firstBlogId;
    }

    private long init() {
        Collection<BlogItem> items = BlogsService.getInstance().getItems();
        items.stream().map(this::makeItem).forEach(getElement()::appendChild);
        return items.iterator().next().getId();
    }

    private Element makeItem(BlogItem item) {
        Element element = new Element(DIV);
        element.getClassList().add("blog-item");

        Element title = new Element(A);
        title.setTextContent(item.getTitle());
        title.setAttribute(ApplicationConstants.ROUTER_LINK_ATTRIBUTE, "");
        title.getClassList().add(TITLE_STYLE);

        StringBuilder link = new StringBuilder(MainLayout.BLOGS);
        link.append('/').append(item.getId());

        title.setAttribute(HREF, link.toString());

        Element by = new Element(DIV);
        by.setTextContent("By " + item.getAuthor());
        by.getStyle().set("display", "inline");

        Element date = new Element(DIV);
        date.getStyle().set("display", "inline");
        date.setTextContent("On " + FORMATTER.format(item.getDate()));

        Element readMore = new Element(A);
        readMore.setTextContent("Read More \u00BB");
        readMore.getClassList().add("read-more");
        readMore.setAttribute(HREF, link.toString());
        readMore.setAttribute(ApplicationConstants.ROUTER_LINK_ATTRIBUTE, "");

        element.appendChild(title, by, date, readMore);

        return element;
    }
}

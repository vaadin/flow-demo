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

import com.vaadin.hummingbird.demo.website.ElementUtils;
import com.vaadin.hummingbird.demo.website.MainLayout;
import com.vaadin.hummingbird.demo.website.SimpleView;
import com.vaadin.hummingbird.demo.website.blogs.backend.BlogRecord;
import com.vaadin.hummingbird.demo.website.blogs.backend.BlogsService;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.router.HasChildView;
import com.vaadin.hummingbird.router.View;
import com.vaadin.shared.ApplicationConstants;
import com.vaadin.ui.UI;

public class BlogsView extends SimpleView implements HasChildView {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yy hh:mm a");

    public BlogsView() {
        super(ElementUtils.createDiv());

        UI.getCurrent().getPage().addStyleSheet("VAADIN/blogs.css");

        Element list = ElementUtils.createDiv();
        list.getClassList().add("blog-list");
        getElement().appendChild(list);
        init(list);
    }

    @Override
    public void setChildView(View childView) {
        if (getElement().getChildCount() > 1) {
            getElement().removeChild(1);
        }
        getElement().appendChild(childView.getElement());
    }

    private long init(Element list) {
        Collection<BlogRecord> items = BlogsService.getInstance().getItems();
        items.stream().map(this::makeItem).forEach(list::appendChild);
        return items.iterator().next().getId();
    }

    private Element makeItem(BlogRecord item) {
        Element element = ElementUtils.createDiv();
        element.getClassList().add("blog-item");

        StringBuilder link = new StringBuilder(MainLayout.BLOGS);
        link.append('/').append(item.getId());

        Element title = ElementUtils.createAnchor(link.toString());
        title.setTextContent(item.getTitle());
        title.setAttribute(ApplicationConstants.ROUTER_LINK_ATTRIBUTE, "");
        title.getClassList().add("blog-item-title");

        Element by = ElementUtils.createDiv();
        by.setTextContent("By " + item.getAuthor());
        by.getStyle().set("display", "inline");

        Element date = ElementUtils.createDiv();
        date.getStyle().set("display", "inline");
        date.setTextContent("On " + FORMATTER.format(item.getDate()));

        Element readMore = ElementUtils.createAnchor(link.toString());
        readMore.setTextContent("Read More \u00BB");
        readMore.getClassList().add("read-more");
        readMore.setAttribute(ApplicationConstants.ROUTER_LINK_ATTRIBUTE, "");

        element.appendChild(title, by, date, readMore);

        return element;
    }

}

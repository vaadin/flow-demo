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
package com.vaadin.hummingbird.demo.website.community.blog;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;

import com.vaadin.hummingbird.demo.website.SimpleView;
import com.vaadin.hummingbird.demo.website.SiteRouterConfigurator;
import com.vaadin.hummingbird.demo.website.community.blog.backend.BlogRecord;
import com.vaadin.hummingbird.demo.website.community.blog.backend.BlogsService;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.router.HasChildView;
import com.vaadin.hummingbird.router.View;

/**
 * The dynamic blogs page.
 *
 * @since
 * @author Vaadin Ltd
 */
public class BlogsView extends SimpleView implements HasChildView {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yy hh:mm a");

    /**
     * Creates a new blogs view.
     */
    public BlogsView() {
        super(ElementFactory.createDiv());

        Element list = ElementFactory.createDiv();
        list.getClassList().add("blog-list");
        getElement().appendChild(list);
        init(list);
        getElement().appendChild(ElementFactory.createDiv());
    }

    @Override
    public void setChildView(View childView) {
        Element childViewElement;
        if (childView != null) {
            childViewElement = childView.getElement();
        } else {
            childViewElement = ElementFactory.createDiv();
        }
        getElement().setChild(1, childViewElement);
    }

    private long init(Element list) {
        Collection<BlogRecord> items = BlogsService.getInstance().getItems();
        items.stream().map(this::makeItem).forEach(list::appendChild);
        return items.iterator().next().getId();
    }

    private Element makeItem(BlogRecord item) {
        Element element = ElementFactory.createDiv();
        element.getClassList().add("blog-item");

        Optional<String> link = SiteRouterConfigurator
                .getNavigablePath(BlogPost.class, "id", "" + item.getId());
        Element title = ElementFactory.createRouterLink(link.get(),
                item.getTitle());
        title.getClassList().add("blog-item-title");

        Element by = ElementFactory.createDiv("By " + item.getAuthor());
        by.getStyle().set("display", "inline");

        Element date = ElementFactory
                .createDiv("On " + FORMATTER.format(item.getDate()));
        date.getStyle().set("display", "inline");

        Element readMore = ElementFactory.createRouterLink(link.get(),
                "Read More \u00BB");
        readMore.getClassList().add("read-more");

        element.appendChild(title, by, date, readMore);

        return element;
    }

}

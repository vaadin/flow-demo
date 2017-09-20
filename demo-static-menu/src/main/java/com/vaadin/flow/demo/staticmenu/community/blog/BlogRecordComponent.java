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

import java.time.format.DateTimeFormatter;

import com.vaadin.flow.demo.staticmenu.community.blog.backend.BlogRecord;
import com.vaadin.ui.html.Div;
import com.vaadin.flow.router.RouterLink;

/**
 * The blog record component.
 *
 * @author Vaadin Ltd
 *
 */
public final class BlogRecordComponent extends Div {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yy hh:mm a");

    /**
     * Creates the record component for the {@code item}.
     *
     * @param item
     *            blog record item
     */
    public BlogRecordComponent(BlogRecord item) {
        setClassName("blog-item");

        RouterLink title = new RouterLink(item.getTitle(), BlogPost.class,
                Long.toString(item.getId()));
        title.setClassName("blog-item-title");

        Div by = new Div();
        by.setText("By " + item.getAuthor());
        by.getStyle().set("display", "inline");

        Div date = new Div();
        date.setText("On " + FORMATTER.format(item.getDate()));
        date.getStyle().set("display", "inline");

        RouterLink readMore = new RouterLink("Read More \u00BB", BlogPost.class,
                Long.toString(item.getId()));
        readMore.setClassName("read-more");

        add(title, by, date, readMore);
    }
}

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
package com.vaadin.hummingbird.demo.website.community.blog.backend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Blogs backend service.
 * 
 * @author Vaadin Ltd
 *
 */
public final class BlogsService {

    private static final BlogsService INSTANCE = new BlogsService();

    private Collection<BlogRecord> records;

    private BlogsService() {
        records = init();
    }

    /**
     * Get service instance.
     * 
     * @return service instance
     */
    public static BlogsService getInstance() {
        return INSTANCE;
    }

    /**
     * Get blog records.
     * 
     * @return blog records
     */
    public Collection<BlogRecord> getItems() {
        return records;
    }

    /**
     * Get record by its {@code id}.
     * 
     * @param id
     *            record id
     * @return blog record if it exists
     */
    public Optional<BlogRecord> getRecord(long id) {
        return records.stream().filter(item -> item.getId() == id).findFirst()
                .map(BlogRecord.class::cast);
    }

    private Collection<BlogRecord> init() {
        Collection<BlogRecord> items = new ArrayList<>();

        BlogRecord item = new BlogRecord();
        item.setAuthor("Jens Jensson");
        item.setDate(LocalDateTime.now());
        item.setId(1L);
        item.setTitle("Vaadin Charts for the Web Component world");
        item.setText(
                "We’re currently building the next version of Vaadin Charts "
                        + "which will include a web component API in addition to the Java API. "
                        + "Vaadin Charts is the first chart library in the world with a comprehensive "
                        + "web component API. Now you can easily add charts to your web pages in a clean "
                        + "and familiar way. With the help of custom elements, it is as easy as writing basic HTML."
                        + "You can try it out already today with the beta releases. This is how to get started in as little as a few minutes.");
        items.add(item);

        item = new BlogRecord();
        item.setAuthor("Matti Tahvonen");
        item.setDate(LocalDateTime.now().minusDays(1));
        item.setId(2L);
        item.setTitle("Vaadin Framework tutorial renewed");
        item.setText(
                "Publishing a new version of a tutorial is not necessarily the most "
                        + "interesting thing for existing Vaadin developers, but we believe this "
                        + "will help many new developers to become addicted to the simplicity of "
                        + "Vaadin development. In case you have friends, who you think should have "
                        + "Vaadin in their toolbox, this is the link you want to share with them. "
                        + "Our previous tutorial was more like a code example with detailed documentation. "
                        + "In this renewed tutorial, the meat is not the final source code of the application, "
                        + "but the learning by doing experience. The renewed tutorial will introduce you to "
                        + "the way to develop Vaadin applications and the core concepts of Vaadin development. "
                        + "The tutorial is written in such a way that you only need to understand the core "
                        + "concepts of software development to understand it. You don't need to be a Java "
                        + "developer to try Vaadin. If you are new to both Java and Vaadin, "
                        + "you should reserve at least an hour to pass the whole tutorial, "
                        + "but more experienced Java developers can finish it even as quickly as 15 minutes. "
                        + "Experienced Java developers can also just pick certain parts of the tutorial, "
                        + "as we provide the starting point for each part of the tutorial.");
        items.add(item);

        item = new BlogRecord();
        item.setAuthor("Matti Tahvonen");
        item.setDate(LocalDateTime.now().minusDays(2));
        item.setId(3L);
        item.setTitle("Community Spotlight - February 2016");
        item.setText(
                "Where do you work and what is your typical working day like? "
                        + "I am a freelance Java-developer and mostly hired by larger "
                        + "companies in the financial or automotive sector, where I "
                        + "support them with their business development projects. "
                        + "That involves programming the UI and backend services, "
                        + "as well as configuring the continuous integration development "
                        + "environment and setting up the initial production deployment setup.");
        items.add(item);

        return items;
    }
}

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

import com.vaadin.hummingbird.demo.website.blogs.backend.BlogRecord;
import com.vaadin.hummingbird.dom.Element;

public class BlogPost {

    private final BlogRecord record;
    private final Element element;

    public BlogPost(BlogRecord record) {
        this.record = record;
        element = new Element(BlogsList.DIV);

        init();
    }

    public Element getElement() {
        return element;
    }

    private void init() {
        Element title = new Element(BlogsList.DIV);
        title.getClassList().add(BlogsList.TITLE_STYLE);
        title.setTextContent(record.getTitle());

        Element text = new Element(BlogsList.DIV);
        text.getClassList().add("blog-content");
        text.setTextContent(record.getText());

        getElement().appendChild(title, text);
    }
}

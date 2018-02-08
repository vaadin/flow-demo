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

import java.util.List;
import java.util.Optional;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.demo.staticmenu.MainLayout;
import com.vaadin.flow.demo.staticmenu.community.blog.backend.BlogRecord;
import com.vaadin.flow.demo.staticmenu.community.blog.backend.BlogsService;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

/**
 * The blog post view.
 *
 * @author Vaadin Ltd
 */
@Route(value = "blog", layout = MainLayout.class)
public class BlogPost extends Div
        implements HasUrlParameter<Long>, HasDynamicTitle {

    private String blogTitle = "";

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        removeAll();

        Optional<BlogRecord> record = BlogsService.getInstance()
                .getRecord(parameter);

        if (!record.isPresent()) {
            event.rerouteToError(IllegalArgumentException.class,
                    getI18NProvider().getTranslation("blog.post.not.found",
                            event.getLocation().getPath()));
        } else {
            blogTitle = record.get().getTitle();
            displayRecord(record.get());
        }
    }

    private void displayRecord(BlogRecord record) {
        HtmlContainer title = new HtmlContainer("h1");
        title.setText(record.getTitle());
        title.setClassName("blog-item-title");

        Div text = new Div();
        text.setText(record.getText());
        text.setClassName("blog-content");

        add(title, text);
    }

    /**
     * Method used to deserialize the list of url segments to an instance of the
     * parameter type. This method can be overridden to support more complex
     * objects as an url parameter. By default this method attempts to cast the
     * first parameter segment to the parameter type and if the parameter list
     * is empty returns null.
     *
     * @param urlParameters
     *            the list of url parameters to deserialize
     * @return the deserialized url parameter, can be {@code null}
     */
    public Long deserializeUrlParameters(List<String> urlParameters) {
        if (urlParameters.isEmpty()) {
            return null;
        }
        Long toReturn = null;
        try {
            toReturn = Long.valueOf(urlParameters.get(0));
        } catch (NumberFormatException nfe) {
            // just return null
        }
        return toReturn;
    }

    @Override
    public String getPageTitle() {
        return blogTitle;
    }
}

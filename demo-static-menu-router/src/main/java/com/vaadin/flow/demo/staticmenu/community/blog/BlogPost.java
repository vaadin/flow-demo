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

import com.vaadin.flow.demo.staticmenu.HomeView;
import com.vaadin.flow.demo.staticmenu.MainLayout;
import com.vaadin.flow.demo.staticmenu.community.blog.backend.BlogRecord;
import com.vaadin.flow.demo.staticmenu.community.blog.backend.BlogsService;
import com.vaadin.router.HasUrlParameter;
import com.vaadin.router.Route;
import com.vaadin.router.Title;
import com.vaadin.router.event.BeforeNavigationEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.common.HtmlContainer;
import com.vaadin.ui.html.Div;

/**
 * The blog post view.
 *
 * @author Vaadin Ltd
 */
@Route(value = "blog", layout = MainLayout.class)
@Title("Blog Post")
public class BlogPost extends Div implements HasUrlParameter<Long> {

    @Override
    public void setParameter(BeforeNavigationEvent event, Long parameter) {
        removeAll();

        if (parameter == null) {
            // FIXME reroute to correct error view
            event.rerouteTo(HomeView.class);
            return;
        }

        BlogRecord record = BlogsService.getInstance().getRecord(parameter)
                .orElse(null);

        if (record == null) {
            // FIXME reroute to correct error view
            event.rerouteTo(HomeView.class);
            return;
        } else {
            HtmlContainer title = new HtmlContainer("h1");
            title.setText(record.getTitle());
            title.setClassName("blog-item-title");

            Div text = new Div();
            text.setText(record.getText());
            text.setClassName("blog-content");

            add(title, text);
        }
    }

    @Override
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
}

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

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.demo.staticmenu.MainLayout;
import com.vaadin.flow.demo.staticmenu.community.blog.backend.BlogRecord;
import com.vaadin.flow.demo.staticmenu.community.blog.backend.BlogsService;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

/**
 * The blog post view.
 *
 * @author Vaadin Ltd
 */
@Route(value = "blog", layout = MainLayout.class)
@PageTitle("Blog Post")
public class BlogList extends Div implements LocaleChangeObserver {

    private Button addBlogPost;

    /**
     * Constructor populating blog listing.
     */
    public BlogList() {
        BlogsService.getInstance().getItems().forEach(this::addRecord);

        // TODO: Update after #2702 implemented
        UI ui = UI.getCurrent();
        addBlogPost = new Button("Add Blog Post", buttonClickEvent -> ui
                .navigate(ui.getRouter().getUrl(BlogCreator.class)));
        add(addBlogPost);
    }

    private void addRecord(BlogRecord record) {
        RouterLink link = new RouterLink(record.getTitle(), BlogPost.class,
                record.getId());
        add(new Div(link));
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        addBlogPost.setText(getTranslation("new.blog.post"));
    }
}

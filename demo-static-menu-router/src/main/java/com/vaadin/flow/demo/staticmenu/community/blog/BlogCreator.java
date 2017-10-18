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

import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatus;
import com.vaadin.flow.demo.staticmenu.MainLayout;
import com.vaadin.flow.demo.staticmenu.community.blog.backend.BlogRecord;
import com.vaadin.flow.demo.staticmenu.community.blog.backend.BlogsService;
import com.vaadin.router.Route;
import com.vaadin.router.event.ActivationState;
import com.vaadin.router.event.BeforeNavigationEvent;
import com.vaadin.router.event.BeforeNavigationListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.button.Button;
import com.vaadin.ui.formlayout.FormLayout;
import com.vaadin.ui.layout.HorizontalLayout;
import com.vaadin.ui.textfield.TextField;

/**
 * Create a new blog post.
 */
@Route(value = "blog/new", layout = MainLayout.class)
public class BlogCreator extends FormLayout
        implements BeforeNavigationListener {

    private final TextField title = new TextField();
    private final TextField content = new TextField();

    private final Button save = new Button("Save");
    private final Button reset = new Button("Reset");

    private final Binder<BlogRecord> binder = new Binder<>();
    private final BlogRecord record = new BlogRecord();

    /**
     * Default constructor.
     */
    public BlogCreator() {
        setResponsiveSteps(new ResponsiveStep("350px", 1));
        addFormItem(title, "Title");
        addFormItem(content, "Blog content");
        content.setWidth("calc(99.999% - 2em)");

        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidth("100%");
        layout.getClassNames().add("buttons");
        layout.add(save, reset);
        add(layout);

        configureComponents();
    }

    @Override
    public void beforeNavigation(BeforeNavigationEvent event) {
        if (ActivationState.DEACTIVATING.equals(event.getActivationState())
                && notValid()) {
            ConfirmationDialog dialog = new ConfirmationDialog();
            getUI().ifPresent(ui -> ui.add(dialog));

            dialog.open("Unsaved changes",
                    "Are you certain you want to continue navigation?",
                    event.postpone());
        }
    }

    private boolean notValid() {
        return binder.hasChanges();
    }

    private void configureComponents() {
        binder.bind(title, BlogRecord::getTitle, BlogRecord::setTitle);
        binder.bind(content, BlogRecord::getText, BlogRecord::setText);

        save.addClickListener(this::save);
        reset.addClickListener(this::reset);
    }

    private void save(Button.ClickEvent<Button> event) {
        if (binder.writeBeanIfValid(record)) {
            BlogsService.getInstance().saveItem(record);
            // TODO: Update after #2702 implemented
            UI ui = UI.getCurrent();
            ui.navigateTo(ui.getRouter().get().getUrl(BlogList.class));
        } else {
            BinderValidationStatus<BlogRecord> validate = binder.validate();
        }
    }

    private void reset(Button.ClickEvent<Button> event) {
        // clear fields via setting <code>null</code>
        binder.readBean(null);
        title.setValue("");
        content.setValue("");
    }
}

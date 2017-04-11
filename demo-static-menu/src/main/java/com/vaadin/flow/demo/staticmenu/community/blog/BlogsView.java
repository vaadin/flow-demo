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

import java.util.Collection;

import com.vaadin.flow.demo.staticmenu.community.blog.backend.BlogRecord;
import com.vaadin.flow.demo.staticmenu.community.blog.backend.BlogsService;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.html.Div;
import com.vaadin.flow.router.HasChildView;
import com.vaadin.flow.router.View;

/**
 * The dynamic blogs page.
 *
 * @since
 * @author Vaadin Ltd
 */
public final class BlogsView extends Div implements HasChildView {

    /**
     * Creates the view.
     */
    public BlogsView() {
        Div list = new Div();
        init(list);
        list.setClassName("blog-list");
        add(list, new Div());
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

    private long init(Div list) {
        Collection<BlogRecord> items = BlogsService.getInstance().getItems();
        items.stream().map(BlogRecordComponent::new).forEach(list::add);
        return items.iterator().next().getId();
    }

}

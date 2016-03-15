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
package com.vaadin.hummingbird.demo.website;

import static com.vaadin.hummingbird.demo.website.Util.createRouterLink;

import java.util.LinkedHashMap;

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.router.HasChildView;
import com.vaadin.hummingbird.router.View;

public class MenuView extends SimpleView implements HasChildView {

    private LinkedHashMap<String, Class<? extends View>> childViews = new LinkedHashMap<>();

    private Element menu;

    public MenuView() {
        super(new Element("div"));
        menu = new Element("div");

        getElement().appendChild(menu);
        getElement().appendChild(new Element("div"));
    }

    protected Element getMenu() {
        return menu;
    }

    protected void addItem(String caption, Class<? extends View> viewClass) {
        childViews.put(caption, viewClass);
        menu.appendChild(createRouterLink(caption, viewClass));
    }

    protected void addItem(String caption, String url) {
        // Won't every have child menus or be selected - just a link
        menu.appendChild(createRouterLink(caption, url));
    }

    @Override
    public void setChildView(View childView) {
        childView.getElement().setAttribute("class", "content");
        getElement().setChild(1, childView.getElement());

        Class<? extends View> childViewClass = childView.getClass();
        markMenuItemSelected(childViewClass);
    }

    protected void markMenuItemSelected(Class<? extends View> childViewClass) {
        // Mark the correct item in the menu as selected
        if (hasChildView(childViewClass)) {
            selectMenuItem(childViewClass);
        } else {
            // Not directly a child selected, try to find the correct one
            // The childView should be found in a chain for one of the
            // registered views
            childViews.forEach((path, view) -> {
                Class<? extends HasChildView>[] chain = Util.getSiteResolver()
                        .getViewChain(view);
                for (int i = 0; i < chain.length; i++) {
                    if (chain[i] == getClass()) {
                        if (i == 0) {
                            continue;
                        }
                        Class<? extends HasChildView> potentialChild = chain[i
                                - 1];
                        if (potentialChild == childViewClass) {
                            selectMenuItem(view);
                        }
                    }
                }
            });
        }
    }

    private boolean hasChildView(Class<? extends View> childViewClass) {
        return childViews.containsValue(childViewClass);
    }

    protected void selectMenuItem(Class<? extends View> childViewClass) {
        String path = Util.getPath(childViewClass).orElse("");
        menu.getChildren().forEach(e -> {
            boolean selected = path.equals(e.getAttribute("href"));
            Util.setClassName(e, "selected", selected);
        });
    }

}

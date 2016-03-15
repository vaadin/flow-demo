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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.router.HasChildView;
import com.vaadin.hummingbird.router.View;

public abstract class MenuView implements View, HasChildView {

    private Set<Class<? extends View>> menuLinkViews = new HashSet<>();

    public MenuView() {
    }

    protected void addItem(String caption, String url) {
        addMenuElement(createRouterLink(caption, url));
    }

    protected void addItem(String caption, Class<? extends View> viewClass) {
        registerMenuLinkView(viewClass);

        Optional<String> path = Util.getPath(viewClass);
        if (path.isPresent()) {
            addMenuElement(createRouterLink(caption, path.get()));
        } else {
            addMenuElement(new Element("span").setTextContent(caption));
        }
    }

    protected void markMenuLinkSelected(Class<? extends View> childViewClass) {
        // Mark the correct item in the menu as selected
        if (hasMenuLinkView(childViewClass)) {
            selectMenuLink(childViewClass);
        } else {
            // Not directly a child selected, try to find the correct one
            // The childView should be found in a chain for one of the
            // views linked from the menu
            Class<? extends View> registeredChildView = findMenuLinkForView(
                    childViewClass);
            selectMenuLink(registeredChildView);
        }
    }

    private Class<? extends View> findMenuLinkForView(
            Class<? extends View> targetView) {
        for (Class<? extends View> menuLinkView : menuLinkViews) {
            Class<? extends HasChildView>[] menuLinkViewChain = Util
                    .getSiteResolver().getViewChain(menuLinkView);
            if (viewChainContains(menuLinkViewChain, targetView)) {
                return menuLinkView;
            }
        }

        return null;
    }

    private static boolean viewChainContains(
            Class<? extends HasChildView>[] menuLinkViewChain,
            Class<? extends View> childViewType) {
        if (menuLinkViewChain == null) {
            return false;
        }

        for (int i = 0; i < menuLinkViewChain.length; i++) {
            if (menuLinkViewChain[i] == childViewType) {
                // Could cache for faster future lookups, even globally for all
                // users
                return true;
            }
        }
        return false;
    }

    protected void registerMenuLinkView(Class<? extends View> viewClass) {
        menuLinkViews.add(viewClass);
    }

    private boolean hasMenuLinkView(Class<? extends View> childViewClass) {
        return menuLinkViews.contains(childViewClass);
    }

    protected void selectMenuLink(Class<? extends View> childViewClass) {
        String path = Util.getPath(childViewClass).orElse("");
        getMenuElements().forEach(e -> {
            boolean selected = path.equals(e.getAttribute("href"));
            Util.setClassName(e, "selected", selected);
        });
    }

    protected abstract void addMenuElement(Element e);

    protected abstract Stream<Element> getMenuElements();

    @Override
    public void setChildView(View childView) {
        // TODO, should be onLocationChange or similar to leave setChildView to
        // the extending class
        Class<? extends View> childViewClass = childView.getClass();
        markMenuLinkSelected(childViewClass);
    }
}

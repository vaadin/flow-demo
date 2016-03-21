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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.router.HasChildView;
import com.vaadin.hummingbird.router.LocationChangeEvent;
import com.vaadin.hummingbird.router.View;

public abstract class MenuView implements View, HasChildView {

    private Map<Class<? extends View>, Map<String, String>> menuLinkViewParameters = new HashMap<>();

    public MenuView() {
    }

    protected void addItem(String caption, String url) {
        addMenuElement(ElementFactory.createRouterLink(caption, url));
    }

    protected void addItem(Class<? extends View> viewClass) {
        addItem(Util.getViewName(viewClass), viewClass);
    }

    protected void addItem(String caption, Class<? extends View> viewClass) {
        addItem(caption, viewClass, null, null);
    }

    protected void addItem(String caption, Class<? extends View> viewClass,
            String parameterKey, String parameterValue) {
        registerMenuLinkView(viewClass, parameterKey, parameterValue);

        Optional<String> path = SiteRouterConfigurator
                .getNavigablePath(viewClass);
        if (path.isPresent()) {
            String url = path.get();
            if (parameterKey != null) {
                url = url.replace("{" + parameterKey + "}", parameterValue);
            }
            if (url == null) {
                addMenuElement(ElementFactory.createSpan(caption));
            } else {
                addMenuElement(ElementFactory.createRouterLink(url, caption));
            }
        } else {
            addMenuElement(ElementFactory.createSpan(caption));
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
        for (Class<? extends View> menuLinkView : menuLinkViewParameters
                .keySet()) {
            Stream<Class<? extends HasChildView>> menuLinkViewChain = Util
                    .getRouterConfiguration().getParentViews(menuLinkView);

            if (menuLinkViewChain.anyMatch(v -> v == targetView)) {
                // Could cache for faster future lookups, even globally for all
                // users
                return menuLinkView;
            }
        }

        return null;
    }

    protected void registerMenuLinkView(Class<? extends View> viewClass) {
        registerMenuLinkView(viewClass, null, null);
    }

    protected void registerMenuLinkView(Class<? extends View> viewClass,
            String parameterKey, String parameterValue) {
        HashMap<String, String> parameterMap = null;
        if (parameterKey != null) {
            parameterMap = new HashMap<>();
            parameterMap.put(parameterKey, parameterValue);
        }
        menuLinkViewParameters.put(viewClass, parameterMap);
    }

    private boolean hasMenuLinkView(Class<? extends View> childViewClass) {
        return menuLinkViewParameters.containsKey(childViewClass);
    }

    protected void selectMenuLink(Class<? extends View> childViewClass) {
        String path = SiteRouterConfigurator.getNavigablePath(childViewClass,
                menuLinkViewParameters.get(childViewClass)).orElse("");
        getMenuElements().forEach(e -> {
            boolean selected = path.equals(e.getAttribute("href"));
            e.getClassList().set("selected", selected);
        });
    }

    protected abstract void addMenuElement(Element e);

    protected abstract Stream<Element> getMenuElements();

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {

        markMenuLinkSelected(
                findChild(getClass(), locationChangeEvent.getViewChain()));
    }

    private Class<? extends View> findChild(Class<? extends View> parentType,
            List<View> viewChain) {
        for (int i = 0; i < viewChain.size(); i++) {
            if (viewChain.get(i).getClass() == parentType) {
                if (i >= 0) {
                    return viewChain.get(i - 1).getClass();
                }
            }
        }
        return null;
    }
}

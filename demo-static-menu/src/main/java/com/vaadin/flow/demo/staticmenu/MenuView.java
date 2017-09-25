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
package com.vaadin.flow.demo.staticmenu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.vaadin.ui.html.Div;
import com.vaadin.ui.html.Label;
import com.vaadin.flow.router.HasChildView;
import com.vaadin.flow.router.LocationChangeEvent;
import com.vaadin.router.RouterLink;
import com.vaadin.flow.router.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.common.HasStyle;

/**
 * Abstract class which keeps track of menu items and handles menu item
 * selection when the user navigates between views.
 *
 * @author Vaadin
 * @since
 */
public abstract class MenuView extends Div implements View, HasChildView {

    private Map<Class<? extends View>, Map<String, String>> menuLinkViewParameters = new HashMap<>();

    /**
     * Creates the view.
     */
    public MenuView() {
    }

    protected final void addItem(Class<? extends View> viewClass) {
        addItem(Util.getViewName(viewClass), viewClass, null, null);
    }

    protected final void addItem(String caption,
            Class<? extends View> viewClass) {
        addItem(caption, viewClass, null, null);
    }

    protected final void addItem(String caption,
            Class<? extends View> viewClass, String parameterValue) {
        if (viewClass == null) {
            addMenuElement(new Label(caption));
        } else if (parameterValue == null) {
            addMenuElement(new RouterLink(caption, viewClass));
        } else {
            addMenuElement(new RouterLink(caption, viewClass, parameterValue));
        }
    }

    protected final void addItem(String caption,
            Class<? extends View> viewClass, String parameterKey,
            String parameterValue) {
        registerMenuLinkView(viewClass, parameterKey, parameterValue);

        addItem(caption, viewClass, parameterValue);
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
                    .getRouterConfiguration()
                    .getParentViewsAscending(menuLinkView);

            if (menuLinkViewChain.anyMatch(v -> v == targetView)) {
                // Could cache for faster future lookups, even globally for all
                // users
                return menuLinkView;
            }
        }

        return null;
    }

    protected final void registerMenuLinkView(Class<? extends View> viewClass) {
        registerMenuLinkView(viewClass, null, null);
    }

    protected final void registerMenuLinkView(Class<? extends View> viewClass,
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
        String path = Util.getNavigablePath(childViewClass,
                menuLinkViewParameters.get(childViewClass)).orElse("");
        getMenuElements().filter(HasStyle.class::isInstance)
                .map(HasStyle.class::cast)
                .forEach(component -> updateSelectClass(component, path));
    }

    protected abstract void addMenuElement(Component component);

    protected abstract Stream<Component> getMenuElements();

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {

        markMenuLinkSelected(
                findChild(getClass(), locationChangeEvent.getViewChain()));
    }

    private void updateSelectClass(HasStyle component, String path) {
        boolean selected = path
                .equals(component.getElement().getAttribute("href"));
        component.setClassName("selected", selected);
    }

    private Class<? extends View> findChild(Class<? extends View> parentType,
            List<View> viewChain) {
        // Child before parent so no need to check first parent
        for (int i = 1; i < viewChain.size(); i++) {
            if (viewChain.get(i).getClass() == parentType) {
                return viewChain.get(i - 1).getClass();
            }
        }
        return null;
    }
}

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

import java.util.Optional;

import com.vaadin.router.HasUrlParameter;
import com.vaadin.router.event.ActivationState;
import com.vaadin.router.event.BeforeNavigationEvent;
import com.vaadin.router.event.BeforeNavigationObserver;
import com.vaadin.ui.Component;
import com.vaadin.ui.html.Div;

/**
 * A menu which adds all items to a single div.
 *
 * @author Vaadin
 */
public class SimpleMenuBar extends MainMenuBar
        implements BeforeNavigationObserver {

    private Div menu;

    /**
     * Creates the view.
     */
    public void init() {
        menu = new Div();
        menu.setClassName("submenu");

        add(menu, new Div());
    }

    protected Div getMenu() {
        return menu;
    }

    /**
     * Add a new menu element to this simple menu.
     *
     * @param navigationTarget
     *            menu element navigation target
     */
    public void addMenuElement(Class<? extends Component> navigationTarget) {
        menu.add(createLink(navigationTarget));
    }

    /**
     * Add a menu element for a navigation target with a parameter.
     *
     * @param navigationTarget
     *            navigation target
     * @param parameter
     *            parameter
     * @param <T>
     *            parameter type
     */
    public <T> void addMenuElement(
            Class<? extends HasUrlParameter<T>> navigationTarget, T parameter) {
        menu.add(createLink(navigationTarget, parameter));
    }

    @Override
    public void beforeNavigation(BeforeNavigationEvent event) {
        if (ActivationState.DEACTIVATING.equals(event.getActivationState())) {
            return;
        }
        clearSelection();
        if (targetExists(event.getNavigationTarget())) {
            activateMenuTarget(event.getNavigationTarget());
        } else {
            StringBuilder path = new StringBuilder();
            for (String segment : event.getLocation().getSegments()) {
                path.append(segment);
                Optional<Class> target = getTargetForPath(path.toString());
                if (target.isPresent()) {
                    activateMenuTarget(target.get());
                    break;
                }
                path.append("/");
            }
        }
    }
}

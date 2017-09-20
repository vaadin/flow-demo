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

import com.vaadin.ui.html.Anchor;
import com.vaadin.ui.html.Div;
import com.vaadin.router.event.ActivationState;
import com.vaadin.router.event.BeforeNavigationEvent;
import com.vaadin.router.event.BeforeNavigationListener;
import com.vaadin.ui.Component;

/**
 * A menu which adds all items to a single div.
 *
 * @author Vaadin
 */
public class SimpleMenuBar extends MainMenuBar implements
        BeforeNavigationListener {

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
     * @param name
     *            name to show
     */
    public void addMenuElement(Class<? extends Component> navigationTarget,
            String name) {
        Anchor link = createLink(navigationTarget, name);
        menu.add(link);
    }

    @Override
    public void beforeNavigation(BeforeNavigationEvent event) {
        if (ActivationState.ACTIVATING.equals(event.getActivationState())) {
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
}

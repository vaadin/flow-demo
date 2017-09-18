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
import java.util.Map;

import com.vaadin.flow.html.Anchor;
import com.vaadin.flow.html.Div;
import com.vaadin.flow.router.event.ActivationState;
import com.vaadin.flow.router.event.BeforeNavigationEvent;
import com.vaadin.flow.router.event.BeforeNavigationListener;
import com.vaadin.ui.Component;

/**
 * Menu view handler. Updates menu item highlights before navigation in the
 * ACTIVATING step.
 *
 * @author Vaadin
 */
public abstract class MainMenuBar extends Div
        implements BeforeNavigationListener {

    private Map<Class<? extends Component>, Anchor> targets = new HashMap<>();
    private Anchor selected;

    /**
     * Build main menu bar
     */
    public MainMenuBar() {
        setClassName("menu");

        init();
    }

    /**
     * Initialize any component extending menu bar
     */
    public abstract void init();

    protected Anchor createLink(Class<? extends Component> navigationTarget,
            String name) {
        Anchor link = new Anchor(Util.getNavigationTargetPath(navigationTarget),
                name);
        link.getElement().setAttribute("router-link", "true");
        targets.put(navigationTarget, link);

        return link;
    }

    // protected abstract void

    @Override
    public void beforeNavigation(BeforeNavigationEvent event) {
        if (ActivationState.ACTIVATING.equals(event.getActivationState())
                && targetExists(event.getNavigationTarget())) {
            clearSelection();
            activateMenuTarget(event.getNavigationTarget());
        }
    }

    /**
     * This menu contains a navigation item for given target.
     * 
     * @param navigationTarget
     *            navigation target
     * @return true/false
     */
    protected boolean targetExists(Class<?> navigationTarget) {
        return targets.containsKey(navigationTarget);
    }

    /**
     * Clear selection for selected menu item.
     */
    protected void clearSelection() {
        if (selected != null) {
            selected.removeClassName("selected");
        }
    }

    /**
     * Activate menu item for navigation target.
     * 
     * @param navigationTarget
     *            navigation target
     */
    protected void activateMenuTarget(Class<?> navigationTarget) {
        Anchor activatedLink = targets.get(navigationTarget);
        activatedLink.addClassName("selected");
        selected = activatedLink;
    }
}

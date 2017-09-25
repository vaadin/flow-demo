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
import java.util.Optional;

import com.vaadin.router.HasUrlParameter;
import com.vaadin.router.RouterLink;
import com.vaadin.ui.Component;
import com.vaadin.ui.html.Div;

/**
 * Menu view handler. Updates menu item highlights before navigation in the
 * ACTIVATING step.
 *
 * @author Vaadin
 */
public abstract class MainMenuBar extends Div {

    private Map<Class<? extends Component>, RouterLink> targets = new HashMap<>();
    private Map<String, Class<? extends Component>> targetPaths = new HashMap<>();
    private RouterLink selected;

    /**
     * Build main menu bar.
     */
    public MainMenuBar() {
        setClassName("menu");

        init();
    }

    /**
     * Initialize any component extending menu bar.
     */
    public abstract void init();

    protected RouterLink createLink(Class<? extends Component> navigationTarget,
            String name) {
        RouterLink link = new RouterLink(name, navigationTarget);
        link.getElement().setAttribute("router-link", "true");
        targets.put(navigationTarget, link);
        targetPaths.put(link.getHref(), navigationTarget);

        return link;
    }

    protected <T> RouterLink createLink(
            Class<? extends HasUrlParameter<T>> navigationTarget, T parameter,
            String name) {
        RouterLink link = new RouterLink(name, navigationTarget, parameter);
        targets.put((Class<? extends Component>) navigationTarget, link);
        targetPaths.put(link.getHref(),
                (Class<? extends Component>) navigationTarget);

        return link;
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
        RouterLink activatedLink = targets.get(navigationTarget);
        activatedLink.addClassName("selected");
        selected = activatedLink;
    }

    protected Optional<Class> getTargetForPath(String path) {
        if (targetPaths.containsKey(path)) {
            return Optional.of(targetPaths.get(path));
        } else if (targetPaths.containsKey(path + "/")) {
            return Optional.of(targetPaths.get(path + "/"));
        }
        return Optional.empty();
    }
}

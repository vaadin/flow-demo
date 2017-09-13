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
 */
public abstract class MenuView extends Div implements BeforeNavigationListener {

    private Map<Class<? extends Component>, Anchor> targets = new HashMap<>();
    private Anchor selected;

    public MenuView() {
        setClassName("menu");

        init();
    }

    public abstract void init();

    protected Anchor createLink(Class<? extends Component> navigationTarget,
            String name) {
        Anchor link = new Anchor(Util.getNavigationTargetPath(navigationTarget),
                name);
        targets.put(navigationTarget, link);

        return link;
    }

    // protected abstract void

    @Override
    public void beforeNavigation(BeforeNavigationEvent event) {
        if (ActivationState.ACTIVATING.equals(event.getActivationState())) {
            if (selected != null) {
                selected.removeClassName("selected");
            }
            if (targets.containsKey(event.getNavigationTarget())) {
                Anchor activatedLink = targets.get(event.getNavigationTarget());
                activatedLink.addClassName("selected");
                selected = activatedLink;
            }
        }
    }
}

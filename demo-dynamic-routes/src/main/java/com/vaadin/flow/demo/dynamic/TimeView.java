/*
 * Copyright 2000-2018 Vaadin Ltd.
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
package com.vaadin.flow.demo.dynamic;

import java.time.LocalDateTime;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.SessionRouteRegistry;
import com.vaadin.flow.server.VaadinSession;

/**
 * Dynamic view that is setup manually.
 * No @Route help or other automation.
 */
public class TimeView extends VerticalLayout {

    public TimeView() {
        Span text = new Span("Time view " + LocalDateTime.now().toString());
        Button back = new Button("Return",
                event -> UI.getCurrent().navigate(""));

        Button remove = new Button("Remove time view",
                event -> SessionRouteRegistry.getSessionRegistry(VaadinSession.getCurrent())
                        .removeRoute(TimeView.class));
        Button reload = new Button("Reload page",
                event -> UI.getCurrent().getPage().reload());

        add(text, back, remove, reload);
    }
}

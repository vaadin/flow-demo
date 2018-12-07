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
package com.vaadin.flow.demo;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.DynamicRoute;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.startup.GlobalRouteRegistry;

@Route("global")
@DynamicRoute
public class GlobalView extends VerticalLayout {

    public GlobalView() {
        Button remove = new Button("Remove from global and reload", event -> {
            GlobalRouteRegistry
                    .getInstance(VaadinServlet.getCurrent().getServletContext())
                    .removeRoute("global");
            UI.getCurrent().getPage().reload();
        });

        RouterLink tologin = new RouterLink("To login", Login.class);

        add(new Span(
                        "This is a view registered from the admin view to the global servlet scope."),
                remove, tologin);
    }
}

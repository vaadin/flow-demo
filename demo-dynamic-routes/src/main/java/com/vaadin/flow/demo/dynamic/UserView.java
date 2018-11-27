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

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.demo.Login;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

/**
 * User view that can be dynamically registered.
 *
 * The {@code DynamicRoute} annotation removes this class from the initial registration
 * and lets us register it later with the default functionality expected of a
 * {@code Route}
 */
@Route(value = "", layout = MainLayout.class)
@DynamicRoute
public class UserView extends Div {

    public UserView() {
        Span text = new Span("This is the view for a logged in user.");
        RouterLink version = new RouterLink("Version", VersionView.class);
        Button logout = new Button("Logout", e -> Login.logout());

        add(text, version, logout);
    }
}

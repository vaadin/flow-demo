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

import javax.servlet.http.HttpServletRequest;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.demo.dynamic.AdminView;
import com.vaadin.flow.demo.dynamic.UserView;
import com.vaadin.flow.demo.dynamic.VersionView;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.startup.RouteRegistry;

@Route("")
public class Login extends Div {

    Span message;
    TextField login;
    PasswordField password;

    public Login() {
        message = new Span();
        message.setVisible(false);

        login = new TextField("Login");
        password = new PasswordField("Password");

        Button submit = new Button("Submit", this::handeLogin);

        add(message, login, password, submit);
    }

    private void handeLogin(ClickEvent<Button> buttonClickEvent) {
        clearMessage();
        if (login.isEmpty() || password.isEmpty()) {
            setMessage("All fields are required.");
        }

        // super secure
        if (!"admin".equals(login.getValue()) && !"user"
                .equals(login.getValue())) {
            setMessage("Faulty username or password!");
            return;
        }

        // Change session id a security measure
        ((HttpServletRequest) VaadinRequest.getCurrent()).changeSessionId();

        // A single Registry should be available that should handle all scopes. Single entrypoint from the UI.
        SessionRouteRegistry sessionRegistry = RouteRegistry
                .getSessionRegistry();

        if ("admin".equals(login.getValue())) {
            // Set route should override global route, but throw if session contains same route.
            sessionRegistry.setRoute(AdminView.class);
            UI.getCurrent().navigate("");
        } else if ("user".equals(login.getValue())) {
            // Set route should override global route, but throw if session contains same route.
            sessionRegistry.setRoute(UserView.class);
            // navigating to where we are should work as setRoute should clear the lastNavigated flag for the UI
            // as the current path may have changed to be something else.
            UI.getCurrent().navigate("");
        }

        // Add the version view to the route for path "version" with the MainLayout as its parent.
        // Note that the parent routes shouldn't be as a list as we can collect parents using
        // RouterUtil.getParentLayoutsForNonRouteTarget(MainLayout.class), though this
        // depends on how dynamic do we want to support. We should anyway be able to request
        // registry for the parts that we need for navigation.
        sessionRegistry.setRoute("version", VersionView.class);
//        sessionRegistry
//                .setRoute("version", VersionView.class, MainLayout.class);

    }

    private void setMessage(String messageText) {
        message.setVisible(true);
        message.setText(messageText);
    }

    private void clearMessage() {
        message.setVisible(false);
    }

    public static void logout() {
        UI.getCurrent().getPage().reload();
        // close session to clear all registered routes.
        // also available as sessionRegistry.clear()
        VaadinSession.getCurrent().close();
    }
}

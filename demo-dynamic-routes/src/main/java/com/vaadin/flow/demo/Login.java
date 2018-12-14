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
import java.util.Arrays;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.demo.dynamic.AdminView;
import com.vaadin.flow.demo.dynamic.TimeView;
import com.vaadin.flow.demo.dynamic.UserView;
import com.vaadin.flow.demo.dynamic.VersionView;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.RouteRegistry;
import com.vaadin.flow.server.SessionRouteRegistry;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.startup.ApplicationRouteRegistry;

/**
 * Login view creates a simple login. With the correct values new routes will be
 * set to the session registry and the sessionId is changed as a security
 * measure.
 */
@Route("")
public class Login extends VerticalLayout {

    private Span message;
    private TextField login;
    private PasswordField password;

    /**
     * Contsructor.
     */
    public Login() {

        message = new Span();
        message.setVisible(false);

        login = new TextField("Login");
        password = new PasswordField("Password");

        Button submit = new Button("Submit", this::handeLogin);

        VerticalLayout usage = new VerticalLayout();
        usage.add(new Span("Login with user [admin|user] with any password."));
        usage.add(new Span("- admin will always navigate to root url"));
        usage.add(new Span("- user will reload and navigate to set url"));

        add(message, login, password, submit, usage);

        if (ApplicationRouteRegistry
                .getInstance(VaadinServlet.getCurrent().getServletContext())
                .getNavigationTarget("global").isPresent()) {
            add(new RouterLink("global", GlobalView.class));
        }

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
        RouteRegistry sessionRegistry = SessionRouteRegistry
                .getSessionRegistry(VaadinSession.getCurrent());

        if ("admin".equals(login.getValue())) {
            // Set route should override global route, but throw if session contains same route.
            RouteUtil.setAnnotatedRoute(AdminView.class, sessionRegistry);
        } else if ("user".equals(login.getValue())) {
            // Set route should override global route, but throw if session contains same route.
            RouteUtil.setAnnotatedRoute(UserView.class, sessionRegistry);
        }

        // Add the version view to the route for path "version" with the MainLayout as its parent.
        // Note that the parent routes shouldn't be as a list as we can collect parents using
        // RouterUtil.getParentLayoutsForNonRouteTarget(MainLayout.class), though this
        // depends on how dynamic do we want to support. We should anyway be able to request
        // registry for the parts that we need for navigation.
        RouteUtil.setRoute("version", VersionView.class, sessionRegistry);

        // Add a view using manually populated parent chain
        sessionRegistry.setRoute("time", TimeView.class,
                Arrays.asList(LooseCenterLayout.class, MainLayout.class));

        // Reload to target url that was navigated to as it may now be registered.
        UI.getCurrent().getPage().reload();
    }

    private void setMessage(String messageText) {
        message.setVisible(true);
        message.setText(messageText);
    }

    private void clearMessage() {
        message.setVisible(false);
    }

    /**
     * Logout logged in user by closing the session and reloading the page.
     * <p>
     * Reload will lead to navigation to LoginView (except if on a application
     * scope target).
     */
    public static void logout() {
        // close session to clear all registered routes.
        // also available as sessionRegistry.clear()
        VaadinSession.getCurrent().close();
        UI.getCurrent().getPage().reload();
    }
}

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

import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.demo.GlobalView;
import com.vaadin.flow.demo.Login;
import com.vaadin.flow.demo.LooseCenterLayout;
import com.vaadin.flow.demo.MainLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.SessionRouteRegistry;
import com.vaadin.flow.server.VaadinSession;

/**
 * Admin view that can be dynamically registered.
 * <p>
 * The {@code DynamicRoute} annotation removes this class from the initial
 * registration
 * and lets us register it later with the default functionality expected of a
 * {@code Route}.
 * <p>
 * AdminView offers the possibility to add/remove Routes from Application and
 * Session scope by selecting and deselecting checkboxes.
 * <p>
 * Note! Session views could also be added to the application scope.
 */
@Route(value = "", layout = MainLayout.class, registerAtStartup = false)
public class AdminView extends VerticalLayout {

    private Map<Class, RouterLink> sessionRoutes = new HashMap<>();

    /**
     * Constructor.
     */
    public AdminView() {
        Span text = new Span("This is the view for a logged in admin.");
        add(text);

        VerticalLayout global = new VerticalLayout();
        global.add(new Span("Application registration routes"));
        populateGlobalSelects(global);

        VerticalLayout session = new VerticalLayout();
        session.add(new Span("Session registration routes"));

        populateSessionSelects(session);

        add(global, session);

        if (SessionRouteRegistry.getSessionRegistry(VaadinSession.getCurrent())
                .getTargetUrl(VersionView.class).isPresent()) {
            RouterLink version = new RouterLink("Version", VersionView.class);
            version.setId("version-link");
            sessionRoutes.put(VersionView.class, version);
        }
        if (SessionRouteRegistry.getSessionRegistry(VaadinSession.getCurrent())
                .getTargetUrl(TimeView.class).isPresent()) {
            RouterLink time = new RouterLink("Time", TimeView.class);
            time.setId("time-link");
            sessionRoutes.put(TimeView.class, time);
        }

        for (RouterLink link : sessionRoutes.values()) {
            add(link);
        }

        Button logout = new Button("Logout", e -> Login.logout());
        logout.setId("logout");

        add(logout);
    }

    private void populateGlobalSelects(VerticalLayout global) {
        global.add(createRegistrationHandler("Global",
                RouteConfiguration.forApplicationScope()
                        .isRouteRegistered(GlobalView.class),
                () -> RouteConfiguration.forApplicationScope()
                        .setAnnotatedRoute(GlobalView.class),
                () -> RouteConfiguration.forApplicationScope()
                        .removeRoute(GlobalView.class)));
    }

    private void populateSessionSelects(VerticalLayout session) {
        session.add(createRegistrationHandler("Version",
                RouteConfiguration.forSessionScope()
                        .isRouteRegistered(VersionView.class),
                () -> addToSessionScope("version", VersionView.class),
                () -> removeFromSession(VersionView.class)));

        session.add(createRegistrationHandler("Time",
                RouteConfiguration.forSessionScope()
                        .isRouteRegistered(TimeView.class),
                () -> addToSessionScope("time", TimeView.class,
                        LooseCenterLayout.class, MainLayout.class),
                () -> removeFromSession(TimeView.class)));
    }

    private void addToSessionScope(String path,
            Class<? extends Component> route,
            Class<? extends RouterLayout>... parents) {
        RouteConfiguration.forSessionScope().setRoute(path, route, parents);

        RouterLink link = new RouterLink(path, route);
        link.setId(path + "-link");
        sessionRoutes.put(route, link);
        addComponentAtIndex(getComponentCount() - 1, link);
    }

    private void removeFromSession(Class<? extends Component> target) {
        RouteConfiguration.forSessionScope().removeRoute(target);
        remove(sessionRoutes.remove(target));
    }

    private Component createRegistrationHandler(String text, boolean registered,
            Runnable register, Runnable unregister) {
        Checkbox status = new Checkbox(text);
        status.setId(text.toLowerCase() + "-checkbox");
        status.setValue(registered);
        status.addValueChangeListener(event -> {
            if (event.getValue() && !event.getOldValue()) {
                register.run();
            } else {
                unregister.run();
            }
        });
        return status;
    }
}

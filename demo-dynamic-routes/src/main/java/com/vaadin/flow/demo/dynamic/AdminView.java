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

import java.util.Arrays;
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
import com.vaadin.flow.router.DynamicRoute;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.RouteRegistry;
import com.vaadin.flow.server.SessionRouteRegistry;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.startup.ApplicationRouteRegistry;

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
@Route(value = "", layout = MainLayout.class)
@DynamicRoute
public class AdminView extends VerticalLayout {

    private Map<Class, RouterLink> sessionRoutes = new HashMap<>();

    /**
     * Constructor.
     */
    public AdminView() {
        Span text = new Span("This is the view for a logged in admin.");
        add(text);

        RouteRegistry globalRegistry = ApplicationRouteRegistry
                .getInstance(VaadinServlet.getCurrent().getServletContext());

        VerticalLayout global = new VerticalLayout();
        global.add(new Span("Application registration routes"));
        populateGlobalSelects(globalRegistry, global);

        RouteRegistry sessionRegistry = SessionRouteRegistry
                .getSessionRegistry(VaadinSession.getCurrent());

        VerticalLayout session = new VerticalLayout();
        session.add(new Span("Session registration routes"));

        populateSessionSelects(sessionRegistry, session);

        add(global, session);

        if (SessionRouteRegistry.getSessionRegistry(VaadinSession.getCurrent())
                .getTargetUrl(VersionView.class).isPresent()) {
            sessionRoutes.put(VersionView.class,
                    new RouterLink("Version", VersionView.class));
        }
        if (SessionRouteRegistry.getSessionRegistry(VaadinSession.getCurrent())
                .getTargetUrl(TimeView.class).isPresent()) {
            sessionRoutes.put(TimeView.class,
                    new RouterLink("Time", TimeView.class));
        }

        for (RouterLink link : sessionRoutes.values()) {
            add(link);
        }

        Button logout = new Button("Logout", e -> Login.logout());

        add(logout);
    }

    private void populateGlobalSelects(RouteRegistry globalRegistry,
            VerticalLayout global) {
        global.add(createRegistrationHandler("Global", GlobalView.class,
                globalRegistry, () -> RouteUtil
                        .setAnnotatedRoute(GlobalView.class, globalRegistry),
                () -> globalRegistry.removeRoute(GlobalView.class)));
    }

    private void populateSessionSelects(RouteRegistry sessionRegistry,
            VerticalLayout session) {
        session.add(createRegistrationHandler("Version", VersionView.class,
                sessionRegistry, () -> {
                    RouteUtil.setRoute("version", VersionView.class,
                            sessionRegistry);
                    RouterLink version = new RouterLink("Version",
                            VersionView.class);
                    sessionRoutes.put(VersionView.class, version);
                    addComponentAtIndex(getComponentCount() - 1, version);
                }, () -> {
                    removeRoute(sessionRegistry, VersionView.class);
                }));
        session.add(createRegistrationHandler("Time", TimeView.class,
                sessionRegistry, () -> {
                    sessionRegistry.setRoute("time", TimeView.class,
                            Arrays.asList(LooseCenterLayout.class,
                                    MainLayout.class));

                    RouterLink time = new RouterLink("Time", TimeView.class);
                    sessionRoutes.put(TimeView.class, time);
                    addComponentAtIndex(getComponentCount() - 1, time);
                }, () -> {
                    removeRoute(sessionRegistry, TimeView.class);
                }));
    }

    private void removeRoute(RouteRegistry registry,
            Class<? extends Component> target) {
        registry.removeRoute(target);
        remove(sessionRoutes.remove(target));
    }

    private Component createRegistrationHandler(String text,
            Class<? extends Component> viewClass, RouteRegistry registry,
            Runnable register, Runnable unregister) {
        Checkbox status = new Checkbox(text);
        status.setValue(registry.getTargetUrl(viewClass).isPresent());
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

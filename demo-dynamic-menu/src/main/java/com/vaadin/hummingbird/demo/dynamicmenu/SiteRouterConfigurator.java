/*
 * Copyright 2000-2016 Vaadin Ltd.
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
package com.vaadin.hummingbird.demo.dynamicmenu;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.router.ModifiableRouterConfiguration;
import com.vaadin.hummingbird.router.RouterConfigurator;
import com.vaadin.hummingbird.router.RouterUI;
import com.vaadin.server.VaadinServlet;

/**
 * Initializes the site by configuring the router to use different views for
 * different URLs.
 *
 * @author Vaadin Ltd
 */
public class SiteRouterConfigurator implements RouterConfigurator {
    /**
     * The main servlet for the application.
     */
    @WebServlet(urlPatterns = "/*", name = "UIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = RouterUI.class, routerConfigurator = SiteRouterConfigurator.class, productionMode = false)
    public static class DemoSiteServlet extends VaadinServlet {
    }

    @Override
    public void configure(ModifiableRouterConfiguration configuration) {
        configuration.setRoute("", HomeView.class, MainLayout.class);
        configuration.setRoute("category/{id}", CategoryView.class,
                MainLayout.class);
        configuration.setRoute("product/{id}", ProductView.class,
                MainLayout.class);
    }
}

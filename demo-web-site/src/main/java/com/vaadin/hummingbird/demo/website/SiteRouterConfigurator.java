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
package com.vaadin.hummingbird.demo.website;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.router.RouterConfiguration;
import com.vaadin.hummingbird.router.RouterConfigurator;
import com.vaadin.server.VaadinServlet;

/**
 * Initializes the site by configuring the router to use different views for
 * different URLs.
 *
 * @author Vaadin Ltd
 */
public class SiteRouterConfigurator implements RouterConfigurator {
    static final String MAPPING_RESOURCE = "resource/*";
    static final String MAPPING_PARAM = "param/{id}";
    static final String MAPPING_DYN_RESOURCE = "dynresource";
    static final String MAPPING_ABOUT = "about";
    static final String MAPPING_HOME = "";

    /**
     * The main servlet for the application.
     */
    @WebServlet(urlPatterns = "/*", name = "UIServlet", asyncSupported = true)
    @VaadinServletConfiguration(routerConfigurator = SiteRouterConfigurator.class, productionMode = false)
    public static class DemoSiteServlet extends VaadinServlet {
    }

    @Override
    public void configure(RouterConfiguration configuration) {
        configuration.setRoute("", HomeView.class, MainLayout.class);
        configuration.setRoute(MAPPING_ABOUT, AboutView.class,
                MainLayout.class);
        configuration.setRoute(MAPPING_PARAM, ParameterView.class,
                MainLayout.class);
        configuration.setRoute(MAPPING_RESOURCE, ResourcesView.class,
                MainLayout.class);
        configuration.setRoute(MAPPING_DYN_RESOURCE, DynamicResourcesView.class,
                MainLayout.class);

        configuration.setErrorView(ErrorView.class, MainLayout.class);
    }
}

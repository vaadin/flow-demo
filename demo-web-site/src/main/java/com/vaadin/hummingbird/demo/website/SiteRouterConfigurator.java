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
import com.vaadin.hummingbird.demo.website.blogs.BlogPost;
import com.vaadin.hummingbird.demo.website.blogs.BlogsView;
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
        // Wildcard just to show all available features in the same demo
        configuration.setRoute("about/*", AboutView.class, MainLayout.class);
        configuration.setRoute("dynamic/{name}", DynamicView.class,
                MainLayout.class);
        configuration.setParentView(BlogsView.class, MainLayout.class);
        configuration.setRoute("blogs/{id}", BlogPost.class, BlogsView.class);
    }
}

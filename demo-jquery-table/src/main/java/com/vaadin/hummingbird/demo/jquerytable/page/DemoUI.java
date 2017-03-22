/*
 * Copyright 2000-2017 Vaadin Ltd.
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
package com.vaadin.hummingbird.demo.jquerytable.page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.WebComponents;
import com.vaadin.hummingbird.demo.jquerytable.persistence.BugrapPersistence;
import com.vaadin.hummingbird.router.RouterConfiguration;
import com.vaadin.hummingbird.router.RouterConfigurator;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * The main UI for the application.
 */
@WebComponents(1)
public class DemoUI extends UI {

    /**
     * The main servlet for the application.
     */
    @WebServlet(urlPatterns = "/*", name = "Servlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = DemoUI.class, routerConfigurator = DemoRouterConfigurator.class, productionMode = false)
    public static class Servlet extends VaadinServlet {
        @Override
        public void init() throws ServletException {
            BugrapPersistence.connect();
            super.init();
        }

    }

    /**
     * The router configurator defines the how to map URLs to views.
     */
    public static class DemoRouterConfigurator implements RouterConfigurator {
        @Override
        public void configure(RouterConfiguration configuration) {
            configuration.setRoute("", ReportsOverview.class);
        }
    }
}

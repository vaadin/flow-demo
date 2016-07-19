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
package com.vaadin.hummingbird.demo.webcomponent.bower;

import java.time.LocalDate;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.demo.webcomponent.bower.component.ProgressBubble;
import com.vaadin.hummingbird.html.Div;
import com.vaadin.hummingbird.router.RouterConfiguration;
import com.vaadin.hummingbird.router.RouterConfigurator;
import com.vaadin.hummingbird.router.View;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Component;

/**
 * Example on how to use a web component.
 *
 * @author Vaadin Ltd
 */
public class MainView extends Div implements View {

    private static final String BACKGROUND = "background";

    /**
     * Creates a new view instance.
     */
    public MainView() {
        ProgressBubble bubble = new ProgressBubble(0, 365);
        bubble.getElement().getStyle().set(BACKGROUND, "green");
        bubble.setValue(LocalDate.now().getDayOfYear());
        add(text("Progress of this year"));
        add(bubble);

        add(new RangeBubble());
    }

    private Component text(String string) {
        Div d = new Div();
        d.setText(string);
        return d;
    }

    /**
     * Servlet for the application.
     */
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(routerConfigurator = RouterConf.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    /**
     * Router configuration for the demo.
     */
    public static class RouterConf implements RouterConfigurator {
        @Override
        public void configure(RouterConfiguration configuration) {
            configuration.setRoute("", MainView.class);
        }
    }
}

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
package com.vaadin.flow.demo.googlesignin.component;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinServlet;

/**
 * The main UI of the application. Configures the servlet and adds a
 * {@link SigninView} to the main page.
 */
@Route("")
public class Signin extends Div {

    /**
     * The main servlet for the application.
     */
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true, initParams = @WebInitParam(name = "google.auth.client.id", value = "http://85635090148-v10q4vim1gc77ngjb8836ikm0dvvefl9.apps.googleusercontent.com/"))
    public static class Servlet extends VaadinServlet {

    }

    public Signin() {
        add(new SigninView());
    }

}
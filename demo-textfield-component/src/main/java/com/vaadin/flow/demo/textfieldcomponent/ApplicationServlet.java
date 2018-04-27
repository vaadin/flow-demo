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
package com.vaadin.flow.demo.textfieldcomponent;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.flow.server.Constants;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinServletConfiguration;

/***
 * Servlet class for using original frontend resources for the project.
 */

@WebServlet(urlPatterns = "/*", name = "ApplicationServlet", initParams = {
        @WebInitParam(name = Constants.USE_ORIGINAL_FRONTEND_RESOURCES, value = "true") })
@VaadinServletConfiguration(productionMode = false)
public class ApplicationServlet extends VaadinServlet {
}

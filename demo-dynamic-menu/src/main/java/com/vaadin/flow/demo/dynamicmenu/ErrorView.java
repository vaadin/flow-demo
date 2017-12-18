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
package com.vaadin.flow.demo.dynamicmenu;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.BeforeNavigationEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.RouteNotFoundError;

/**
 * An error view showing a message with the wrong location to the user.
 */
public class ErrorView extends RouteNotFoundError {

    @Override
    public int setErrorParameter(BeforeNavigationEvent event,
            ErrorParameter<NotFoundException> parameter) {
        getElement().getStyle().set("textAlign", "center");
        getElement().appendChild(
                ElementFactory.createHeading1("404 - Page not found"),
                ElementFactory.createSpan("Please check the location "),
                ElementFactory.createEmphasis(event.getLocation().getPath()),
                ElementFactory.createSpan(" and try again."));

        return HttpServletResponse.SC_NOT_FOUND;
    }
}

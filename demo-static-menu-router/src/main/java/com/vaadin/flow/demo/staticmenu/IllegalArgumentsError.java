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
package com.vaadin.flow.demo.staticmenu;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.router.ErrorParameter;
import com.vaadin.router.HasErrorParameter;
import com.vaadin.router.ParentLayout;
import com.vaadin.router.event.BeforeNavigationEvent;
import com.vaadin.ui.html.Div;
import com.vaadin.ui.html.Label;

/**
 * Error view for showing illegal argument information.
 */
@ParentLayout(MainLayout.class)
public class IllegalArgumentsError extends Div
        implements HasErrorParameter<IllegalArgumentException> {

    /**
     * Default constructor.
     */
    public IllegalArgumentsError() {
        setId("error-content");
    }

    @Override
    public int setErrorParameter(BeforeNavigationEvent event,
            ErrorParameter<IllegalArgumentException> parameter) {
        Label message = new Label(parameter.getCustomMessage());
        add(message);

        return HttpServletResponse.SC_NOT_FOUND;
    }
}

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

import com.vaadin.ui.html.Div;
import com.vaadin.ui.html.Emphasis;
import com.vaadin.ui.html.H1;
import com.vaadin.ui.html.Span;
import com.vaadin.flow.router.LocationChangeEvent;
import com.vaadin.flow.router.View;

/**
 * An error view showing a message with the wrong location to the user.
 */
public class ErrorView extends Div implements View {

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        add(new H1("404 - Page not found"),
                new Span("Please check the location "),
                new Emphasis(locationChangeEvent.getLocation().getPath()),
                new Span(" and try again."));
    }
}

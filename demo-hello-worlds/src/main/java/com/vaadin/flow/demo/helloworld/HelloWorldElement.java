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
package com.vaadin.flow.demo.helloworld;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Hello World view based on elements.
 */
@PageTitle("Hello World with elements")
@Route(value = "element", layout = MainLayout.class)
public class HelloWorldElement extends Component {

    public static final String VALUE_PROPERTY = "value";

    /**
     * Element demo constructor.
     */
    public HelloWorldElement() {
        // Set the root of the view to be a div element
        super(ElementFactory.createDiv());

        // Add an HTML import for the paper-input element and create one
        UI.getCurrent().getPage().addHtmlImport(
                "frontend://bower_components/paper-input/paper-input.html");
        Element input = new Element("paper-input");

        Element greeting = ElementFactory.createDiv();

        // Set the initial greeting value
        updateGreeting(input, greeting);

        /*
         * Automatically send the current value of the "value" property to the
         * server whenever a value-changed event is fired by the paper-input
         * element.
         */
        input.synchronizeProperty(VALUE_PROPERTY, "value-changed");

        // Listen for value change events
        input.addPropertyChangeListener(VALUE_PROPERTY,
                event -> updateGreeting(input, greeting));

        // Set up DOM id values used for integration tests
        input.setAttribute("id", "inputId");
        greeting.setAttribute("id", "elementsGreeting");

        /*
         * Add the input and the greeting to the div element we passed to the
         * constructor
         */
        getElement().appendChild(input, greeting);
    }

    private void updateGreeting(Element input, Element greeting) {
        String name = input.getProperty(VALUE_PROPERTY, "");

        // Update the element based on the name
        if (name.isEmpty()) {
            greeting.setText("Please enter your name");
        } else {
            greeting.setText(String.format("Hello %s!", name));
        }
    }
}

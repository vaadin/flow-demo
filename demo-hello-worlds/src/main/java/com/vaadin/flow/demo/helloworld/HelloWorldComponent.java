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
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.Synchronize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

/**
 * Hello World view based on components. This is a composite based on a div
 * component.
 */
@PageTitle("Hello World with components")
@Route(value = "component", layout = MainLayout.class)
public class HelloWorldComponent extends Composite<Div> {
    /**
     * Creates the hello world Components API based component.
     */
    public HelloWorldComponent() {
        // Create instance of the custom PaperInput component (defined below)
        PaperInput input = new PaperInput();

        // Create instance of the built-in div component
        Div greeting = new Div();

        // Set the initial greeting value
        updateGreeting(input, greeting);

        // Listen for value change events
        input.addValueChangeListener(event -> updateGreeting(input, greeting));

        // Set up DOM id values used for integration tests
        input.setId("inputId");
        greeting.setId("componentsGreeting");

        /*
         * Add the input and the greeting to the Div component that makes up the
         * content of this Composite subclass
         */
        getContent().add(input, greeting);
    }

    private void updateGreeting(PaperInput input, Div greeting) {
        String name = input.getValue();

        // Update the component based on the name
        if (name.isEmpty()) {
            greeting.setText("Please enter your name");
        } else {
            greeting.setText(String.format("Hello %s!", name));
        }
    }

    /**
     * Custom component implementation for interacting with the paper-input web
     * component.
     */
    @Tag("paper-input")
    @HtmlImport("frontend://bower_components/paper-input/paper-input.html")
    public static class PaperInput extends Component {
        /**
         * Automatically send the current value of the "value" property to the
         * server whenever a value-changed event is fired by the paper-input
         * element.
         */
        @Synchronize("value-changed")
        public String getValue() {
            return getElement().getProperty("value", "");
        }

        /**
         * Adds a listener that is automatically hooked up with a DOM event
         * based on annotations on the event class defined below.
         * 
         * @param listener
         *            value change listener to add
         * @return registration to remove listener with
         */
        public Registration addValueChangeListener(
                ComponentEventListener<ValueChangeEvent> listener) {
            return addListener(ValueChangeEvent.class, listener);
        }
    }

    /**
     * Custom event that is automatically connected to value-changed events from
     * the root element of the component for which listeners are added.
     */
    @DomEvent("value-changed")
    public static class ValueChangeEvent extends ComponentEvent<PaperInput> {
        /**
         * Creates a new event using the given source and indicator whether the
         * event originated from the client side or the server side.
         * 
         * @param source
         *            the source component
         * @param fromClient
         *            <code>true</code> if the event originated from the client
         *            side, <code>false</code> otherwise
         */
        public ValueChangeEvent(PaperInput source, boolean fromClient) {
            super(source, fromClient);
        }
    }

}

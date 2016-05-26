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
package com.vaadin.hummingbird.demo.textfieldcomponent;

import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.dom.EventRegistrationHandle;
import com.vaadin.hummingbird.event.ComponentEventListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentEvent;

/**
 * A component which consist of a label and an input field.
 *
 * @author Vaadin
 * @since
 */
@Tag("div")
public class TextField extends Component {

    private static final String VALUE_PROPERTY = "value";
    private static int id = 0;
    private Element labelElement;
    private Element inputElement;

    /**
     * Create an empty text field without a label.
     */
    public TextField() {
        labelElement = ElementFactory.createLabel();
        inputElement = ElementFactory.createInput();

        String textFieldId = generateId();
        inputElement.setAttribute("id", textFieldId);
        labelElement.setAttribute("for", textFieldId);

        inputElement.synchronizeProperty(VALUE_PROPERTY, "change");
        inputElement.addEventListener("change",
                e -> fireEvent(new ChangeEvent(this, true)));

        getElement().appendChild(labelElement, inputElement);
    }

    /**
     * Creates an empty text field with the given label.
     *
     * @param label
     *            the label to use
     */
    public TextField(String label) {
        this();
        setLabel(label);
    }

    /**
     * Generates a unique enough id for an element.
     *
     * @return a unique id
     */
    private static String generateId() {
        return "gen-" + id++;
    }

    /**
     * Gets the label for the text field.
     *
     * @return the text field label
     */
    public String getLabel() {
        return labelElement.getOwnTextContent();
    }

    /**
     * Sets the label for the text field.
     *
     * @param label
     *            the text field label
     */
    public void setLabel(String label) {
        labelElement.setTextContent(label);
    }

    /**
     * Gets the value of the input field.
     *
     * @return the value of the input field
     */
    public String getValue() {
        return inputElement.getProperty(VALUE_PROPERTY, "");
    }

    /**
     * Sets the value of the input field.
     *
     * @param value
     *            the value of the input field
     */
    public void setValue(String value) {
        inputElement.setProperty(VALUE_PROPERTY, value);
        fireEvent(new ChangeEvent(this, false));
    }

    /**
     * Event fired when the value of the text field changes.
     */
    public static class ChangeEvent extends ComponentEvent<TextField> {
        private String newValue;

        /**
         * Creates an event using the given source and information about the
         * event origin.
         *
         * @param source
         *            the text field which fired the event
         * @param fromClient
         *            <code>true</code> if the event originated from the client
         *            side, <code>false</code> otherwise
         */
        public ChangeEvent(TextField source, boolean fromClient) {
            super(source, fromClient);
            newValue = source.getValue();
        }

        /**
         * Gets the value of the text field after the change.
         *
         * @return the new value of the text field
         */
        public String getNewValue() {
            return newValue;
        }
    }

    /**
     * Adds a listener which is fired when the value of the text field changes.
     *
     * @param changeListener
     *            the listener to add
     * @return a handle which can be used to remove the listener
     */
    public EventRegistrationHandle addChangeListener(
            ComponentEventListener<ChangeEvent> changeListener) {
        return addListener(ChangeEvent.class, changeListener);
    }
}

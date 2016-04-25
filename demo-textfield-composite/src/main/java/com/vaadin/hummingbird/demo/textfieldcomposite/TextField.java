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
package com.vaadin.hummingbird.demo.textfieldcomposite;

import com.vaadin.hummingbird.dom.EventRegistrationHandle;
import com.vaadin.hummingbird.event.ComponentEventListener;
import com.vaadin.hummingbird.html.Div;
import com.vaadin.hummingbird.html.Input;
import com.vaadin.hummingbird.html.Label;
import com.vaadin.hummingbird.html.event.ChangeEvent;
import com.vaadin.ui.Composite;

/**
 * A text field component consisting of a label and an input field.
 * <p>
 * Internally implemented using a {@link Label} and an {@link Input} component
 * combined using a {@link Div}. The TextField class extends {@link Composite}
 * instead of {@link Div} to be able to hide the public API of the {@link Div}
 * class and instead provide a more high level API.
 *
 * @author Vaadin Ltd
 * @since
 */
public class TextField extends Composite<Div> {

    private static int id = 0;
    private Div layout;
    private Label label;
    private Input input;

    /**
     * Creates an empty text field without a label.
     */
    public TextField() {
        layout = new Div();
        label = new Label();
        label.getStyle().set("display", "block");
        input = new Input();
        input.setId(generateId());
        label.setFor(input);
        layout.add(label, input);
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

    @Override
    protected Div initContent() {
        return layout;
    }

    /**
     * Gets the label for the text field.
     *
     * @return the text field label
     */
    public String getLabel() {
        return label.getText();
    }

    /**
     * Sets the label for the text field.
     *
     * @param label
     *            the text field label
     */
    public void setLabel(String label) {
        this.label.setText(label);
    }

    /**
     * Gets the value of the input field.
     *
     * @return the value of the input field
     */
    public String getValue() {
        return input.getValue();
    }

    /**
     * Sets the value of the input field.
     *
     * @param value
     *            the value of the input field
     */
    public void setValue(String value) {
        input.setValue(value);
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
        return input.addChangeListener(changeListener);
    }

}

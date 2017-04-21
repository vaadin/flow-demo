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
package com.vaadin.flow.demo.helloworld.element;

import com.vaadin.annotations.DomEvent;
import com.vaadin.annotations.Synchronize;
import com.vaadin.annotations.Tag;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.dom.EventRegistrationHandle;
import com.vaadin.flow.event.ComponentEventListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentEvent;

/**
 * Native select element for selecting items.
 */
@Tag("select")
public class SelectElement extends Component {

    /**
     * Init select element with the selections given.
     * 
     * @param options
     *            select options to populate select with
     */
    public SelectElement(String... options) {
        for (String selection : options) {
            Element option = ElementFactory.createOption(selection);
            getElement().appendChild(option);
        }
    }

    /**
     * Get the current selection.
     * 
     * @return currently selected value
     */
    @Synchronize("change")
    public String getValue() {
        return getElement().getProperty("value");
    }

    /**
     * Set selected value for Select.
     * 
     * @param value
     *            value to set selected
     */
    public void setValue(String value) {
        getElement().setProperty("value", value);
    }

    /**
     * Add a change listener for select.
     * 
     * @param listener
     *            change listener
     * @return registration handle for removing listener
     */
    public EventRegistrationHandle addChangeListener(
            ComponentEventListener<ChangeEvent> listener) {
        return addListener(ChangeEvent.class, listener);
    }

    /**
     * ComponentEvent for change event in the dom.
     */
    @DomEvent("change")
    public static class ChangeEvent extends ComponentEvent<SelectElement> {
        public ChangeEvent(SelectElement source, boolean fromClient) {
            super(source, fromClient);
        }
    }
}

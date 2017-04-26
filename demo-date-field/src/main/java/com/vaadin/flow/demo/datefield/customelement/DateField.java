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
package com.vaadin.flow.demo.datefield.customelement;

import java.time.LocalDate;
import java.util.stream.IntStream;

import com.vaadin.annotations.Tag;
import com.vaadin.flow.dom.EventRegistrationHandle;
import com.vaadin.flow.event.ComponentEventListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentEvent;

import com.vaadin.flow.dom.ShadowRoot;

/**
 * Custom element date-field for selecting dates.
 */
@Tag("date-field")
public class DateField extends Component {

    private SelectElement day, month, year;

    private ShadowRoot shadowRoot;

    /**
     * Construct a date field component <date-field></date-field>.
     * <p>
     * The actual dom will be:
     * <p>
     * <date-field>
     *     #shadowroot
     *          <select>...</select>
     *          <select>...</select>
     *          <select>...</select>
     * </date-field>
     */
    public DateField() {
        // Create shadow root for element
        shadowRoot = getElement().attachShadow();

        // Init selects for day, month and year.
        day = new SelectElement(IntStream.range(1, 32)
                .mapToObj(Integer::toString).toArray(String[]::new));
        month = new SelectElement(IntStream.range(1, 13)
                .mapToObj(Integer::toString).toArray(String[]::new));
        year = new SelectElement(IntStream.range(1900, 2500)
                .mapToObj(Integer::toString).toArray(String[]::new));

        // "Fire" value change to callback listeners
        day.addChangeListener(event -> valueChange());
        month.addChangeListener(event -> valueChange());
        year.addChangeListener(event -> valueChange());

        // Add selectors to shadow tree
        shadowRoot.appendChild(day.getElement());
        shadowRoot.appendChild(month.getElement());
        shadowRoot.appendChild(year.getElement());
    }

    /**
     * Get current date selection.
     * 
     * @return date for current selections
     */
    public LocalDate getValue() {
        return LocalDate.of(Integer.parseInt(year.getValue()),
                Integer.parseInt(month.getValue()),
                Integer.parseInt(day.getValue()));
    }

    private void valueChange() {
        fireEvent(new ValueChangeEvent(this, false));
    }

    /**
     * Add a value change listener for date field.
     * 
     * @param valueChangeListener
     *            value change listener
     * @return registration handle for removing listener
     */
    public EventRegistrationHandle addValueChangeListener(
            ComponentEventListener<ValueChangeEvent> valueChangeListener) {
        return addListener(ValueChangeEvent.class, valueChangeListener);
    }

    /**
     *  Event triggered when a DateField value changes.
     */
    public static class ValueChangeEvent extends ComponentEvent<DateField> {

        /**
         * Creates a new event using the given source and client information.
         *
         * @param source
         *            the cell which was clicked
         * @param fromClient
         *            <code>true</code> if the event originated from the client
         *            side, <code>false</code> otherwise
         */
        public ValueChangeEvent(DateField source, boolean fromClient) {
            super(source, fromClient);
        }
    }
}

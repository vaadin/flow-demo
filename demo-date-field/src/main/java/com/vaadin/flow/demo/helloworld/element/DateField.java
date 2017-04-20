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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.vaadin.annotations.Tag;
import com.vaadin.ui.Component;

/**
 * Cusom element date-field for selecting dates.
 */
@Tag("date-field")
public class DateField extends Component {

    SelectElement day, month, year;

    ShadowRoot shadowRoot;
    List<ValueChangeCallback> callbacks = new ArrayList<>(0);

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
        shadowRoot.appendElement(day.getElement());
        shadowRoot.appendElement(month.getElement());
        shadowRoot.appendElement(year.getElement());
    }

    /**
     * Get current date selection.
     * 
     * @return LocalDate of current selection
     */
    public LocalDate getValue() {
        return LocalDate.of(Integer.parseInt(day.getValue()),
                Integer.parseInt(month.getValue()),
                Integer.parseInt(year.getValue()));
    }

    private void valueChange() {
        callbacks.forEach(ValueChangeCallback::valueChange);
    }

    /**
     * Add a callback that will be called when value changes.
     * 
     * @param callback
     *            Value change callback
     * @return CallbackRemover to remove callback
     */
    public CallbackRemover addValueChangeListener(
            ValueChangeCallback callback) {
        callbacks.add(callback);
        return () -> callbacks.remove(callback);
    }

    /**
     * Interface for ValueChangeCallback.
     */
    @FunctionalInterface
    public interface ValueChangeCallback {
        void valueChange();
    }

    /**
     * Interface for removing callback.
     */
    @FunctionalInterface
    public interface CallbackRemover {
        void remove();
    }
}

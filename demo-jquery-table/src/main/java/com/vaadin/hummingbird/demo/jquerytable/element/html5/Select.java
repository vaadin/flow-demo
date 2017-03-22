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
package com.vaadin.hummingbird.demo.jquerytable.element.html5;

import java.util.Optional;

import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.html.HtmlComponent;
import com.vaadin.hummingbird.html.event.ChangeNotifier;

/**
 * Component representing a <code>&lt;select&gt;</code> element.
 *
 * @see Option
 */
@Tag(Tag.SELECT)
public class Select extends HtmlComponent implements ChangeNotifier {

    /**
     * Creates an empty select.
     */
    public Select() {
        this.getElement().addSynchronizedPropertyEvent("change");
        this.getElement().addSynchronizedProperty("value");
    }

    /**
     * Adds an option to this select.
     * 
     * @param option
     *            A not null Option
     */
    public void addOption(Option option) {
        assert option != null : "The option cannot be null.";
        getElement().appendChild(option.getElement());
    }

    /**
     * Gets the selected Option, if any.
     * 
     * @return the selected Option component, based on the
     *         {@link #getSelectedValue()}
     */
    public Optional<Option> getSelectedOption() {
        Optional<String> selectedValue = getSelectedValue();
        if (selectedValue.isPresent()) {
            Optional<Element> first = getElement().getChildren().filter(el -> el
                    .getComponent().isPresent()
                    && el.getComponent().get() instanceof Option
                    && selectedValue.get().equals(
                            ((Option) el.getComponent().get()).getValue()))
                    .findFirst();
            return first.map(Option.class::cast);
        }
        return Optional.empty();
    }

    /**
     * Gets the selected String value, if any.
     * 
     * @return the selected value in the select element
     */
    public Optional<String> getSelectedValue() {
        Element el = getElement();
        String selectedValue = el.getProperty("value");
        return Optional.ofNullable(selectedValue);
    }

}

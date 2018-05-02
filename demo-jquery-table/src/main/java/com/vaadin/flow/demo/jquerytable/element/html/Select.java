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
package com.vaadin.flow.demo.jquerytable.element.html;

import java.util.Optional;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.dom.Element;

/**
 * Component representing a <code>&lt;select&gt;</code> element.
 *
 * @see Option
 */
@Tag(Tag.SELECT)
public class Select extends AbstractSinglePropertyField<Select, String> {

    public static final String VALUE_PROPERTY = "value";

    /**
     * Creates an empty select.
     */
    public Select() {
        super(VALUE_PROPERTY, "", false);
        getElement().synchronizeProperty(VALUE_PROPERTY, "change");
    }

    /**
     * Adds an option to this select.
     * 
     * @param option
     *            A not <code>null</code> Option
     */
    public void addOption(Option option) {
        if (option != null) {
            getElement().appendChild(option.getElement());
        }
    }

    /**
     * Gets the selected String value, if any.
     * 
     * @return the selected value in the select element
     */
    public Optional<String> getSelectedValue() {
        Element el = getElement();
        String selectedValue = el.getProperty(VALUE_PROPERTY);
        return Optional.ofNullable(selectedValue);
    }

}

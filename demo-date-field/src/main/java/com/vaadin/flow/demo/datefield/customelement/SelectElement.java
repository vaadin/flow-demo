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

import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.ui.common.HtmlComponent;
import com.vaadin.ui.event.ChangeNotifier;
import com.vaadin.ui.event.Synchronize;
import com.vaadin.ui.Tag;

/**
 * Native select element for selecting items.
 */
@Tag("select")
public class SelectElement extends HtmlComponent implements ChangeNotifier {

    /**
     * Init select element with the selections given.
     * 
     * @param options
     *            select options to populate select with
     */
    public SelectElement(String... options) {
        if (options.length == 0) {
            throw new IllegalArgumentException(
                    "Select should be given at least one option");
        }

        for (String selection : options) {
            Element option = ElementFactory.createOption(selection);
            getElement().appendChild(option);
        }
        setValue(options[0]);
        getElement().getChild(0).setProperty("selected", true);
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
}

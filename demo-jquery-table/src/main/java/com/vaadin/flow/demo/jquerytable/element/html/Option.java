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

import com.vaadin.ui.common.HasText;
import com.vaadin.ui.common.HtmlComponent;
import com.vaadin.ui.common.PropertyDescriptor;
import com.vaadin.ui.common.PropertyDescriptors;
import com.vaadin.ui.Tag;

/**
 * Component representing a <code>&lt;option&gt;</code> element.
 */
@Tag(Tag.OPTION)
public class Option extends HtmlComponent implements HasText {

    private static final PropertyDescriptor<String, String> valueDescriptor = PropertyDescriptors
            .propertyWithDefault("value", "");

    /**
     * Sets the model value of the option.
     * 
     * @param value
     *            the model value
     */
    public void setValue(String value) {
        set(valueDescriptor, value);
    }

    /**
     * Gets the model value of the option.
     * 
     * @return the model value
     */
    public String getValue() {
        return get(valueDescriptor);
    }

}

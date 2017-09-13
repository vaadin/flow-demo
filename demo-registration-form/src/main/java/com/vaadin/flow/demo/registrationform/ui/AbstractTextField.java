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
package com.vaadin.flow.demo.registrationform.ui;

import java.util.Optional;

import com.vaadin.ui.HasSize;

/**
 * Abstract component with common text field functionality.
 *
 * @param <T>
 *            the Flow text field component type
 *
 * @author Vaadin Ltd
 *
 */
public interface AbstractTextField<T> extends HasSize {

    /**
     * A hint to the user of what can be entered in the control.
     *
     * @param placeholder
     *            the String value to set
     * @return this instance, for method chaining
     */
    void setPlaceholder(String placeholder);

    /**
     * Sets an arbitrary data into the instance.
     *
     * @see #getData()
     *
     * @param object
     *            a data to set
     */
    void setData(Object object);

    /**
     * Gets the data set via {@link #setData(Object)}.
     *
     * @see #setData(Object)
     * @return the stored data
     */
    Object getData();

    /**
     * Gets the id of the component.
     *
     * @return the element id.
     */
    Optional<String> getId();
}

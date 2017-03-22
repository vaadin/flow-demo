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
package com.vaadin.hummingbird.demo.jquerytable.element.tablesorter;

import java.io.Serializable;
import java.util.List;

/**
 * Column definition used in conjunction with {@link RichTable}.
 * 
 * @param T
 *            the type of the model object used with this column
 * @see RichTable#setColumns(List)
 */
public interface RichColumn<T> extends Serializable {

    /**
     * Gets the name of the column, which is rendered at the table header.
     * 
     * @return a not null name. Empty String is allowed.
     */
    String getColumnName();

    /**
     * Gets the CSS classes applied to the column at the table header.
     * 
     * @return a list of classes. Can be null or empty.
     */
    List<String> getColumnClasses();

    /**
     * Gets the value of the object to be used in sorting and grouping. When
     * null, the value used for these widgets is the one returned by
     * {@link #getRenderedValue(Object)}.
     * 
     * @param object
     *            The model object, fetched from the {@link DataProvider}
     * @return the model value.
     */
    String getModelValue(T object);

    /**
     * Gets the value of the object to be rendered at the table body. This is
     * effectively what the user sees in the table.
     * 
     * @param object
     *            The model object, fetched from the {@link DataProvider}
     * @return the rendered value. Null Strings will be rendered as "null"
     */
    String getRenderedValue(T object);

}

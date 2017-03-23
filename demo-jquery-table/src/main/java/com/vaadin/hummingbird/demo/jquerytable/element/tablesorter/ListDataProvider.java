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
package com.vaadin.hummingbird.demo.jquerytable.element.tablesorter;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Class responsible of providing data objects to the {@link RichTable}. This
 * class is also responsible for resolving the ID of the objects when requested
 * by the table.
 * 
 * @param <T>
 *            the type of the model object used with this data provider
 * 
 */
public abstract class ListDataProvider<T extends Serializable>
        implements Serializable {

    private List<T> data = Collections.emptyList();

    /**
     * Sets the internal List used by the {@link RichTable}.
     * 
     * @param data
     *            List of objects.
     * @see RichTable#updateContent()
     */
    public void setData(List<T> data) {
        if (data == null) {
            data = Collections.emptyList();
        }
        this.data = data;
    }

    /**
     * Gets the internal list of items.
     * 
     * @return the list, can be empty, but never <code>null</code>.
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Gets the String representation of the identifier of a particular object.
     * This identifier should be unique, not <code>null</code> and immutable
     * among all objects provided by the same data provider.
     * 
     * @param object
     *            Object returned by the {@link #getNext()} method
     * @return the unique ID of the object
     */
    public abstract String getId(T object);

}

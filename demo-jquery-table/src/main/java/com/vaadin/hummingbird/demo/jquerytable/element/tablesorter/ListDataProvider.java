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
import java.util.function.Function;

import com.vaadin.server.SerializableFunction;

/**
 * Class responsible of providing data objects to the {@link RichTable}. This
 * class is also responsible for resolving the ID of the objects when requested
 * by the table.
 * 
 * @param <T>
 *            the type of the model object used with this data provider
 * 
 */
public class ListDataProvider<T extends Serializable> implements Serializable {

    private List<T> data = Collections.emptyList();
    private SerializableFunction<T, String> idFunction;

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
     * Sets the {@link Function} capable of getting the unique identifier for a
     * given object. This identifier should be unique, not <code>null</code> and
     * immutable among all objects provided by the same data provider.
     * 
     * @param idFunction
     *            The function that receives an object and return its ID in
     *            String form.
     */
    public void setIdFunction(SerializableFunction<T, String> idFunction) {
        this.idFunction = idFunction;
    }

    /*
     * Internal method called by RichTable.
     */
    String getId(T object) {
        return idFunction.apply(object);
    }

}

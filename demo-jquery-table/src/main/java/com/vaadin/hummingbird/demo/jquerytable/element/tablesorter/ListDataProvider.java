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

import java.util.List;
import java.util.Optional;

/**
 * Convenience concrete class for the {@link DataProvider}, that uses an
 * internal {@link List} to provide the items.
 * 
 */
public class ListDataProvider<T> implements DataProvider<T> {

    private int index = 0;
    private List<T> data;

    /**
     * Sets the internal List used by the {@link #getNext()} method. After
     * setting thre list, the iteration is reset. In other words, the
     * {@link #getNext()} method will return the first element of the list.
     * 
     * @param data
     *            not null List of objects
     * @see RichTable#updateContent()
     */
    public void setData(List<T> data) {
        assert data != null : "The data list cannot be null.";
        this.data = data;
        index = 0;
    }

    /**
     * Gets the internal list of items.
     * 
     * @return the list, or <code>null</code> if no list was set
     */
    public List<T> getData() {
        return data;
    }

    @Override
    public Optional<T> getNext() {
        if (data == null || index >= data.size()) {
            return Optional.empty();
        }
        return Optional.of(data.get(index++));
    }

}

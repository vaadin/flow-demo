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
import java.util.Optional;

/**
 * Interface that establishes the communication interface between the
 * {@link RichTable} and the data repository.
 * 
 * @param T
 *            the type of the model object used with this data provider
 */
public interface DataProvider<T> extends Serializable {

    /**
     * Gets the next item from the data repository.
     * 
     * @return the next item
     */
    Optional<T> getNext();

    /**
     * Gets the String representation of the identifier of a particular object.
     * This identifier should be unique, not <code>null</code> and immutable
     * among all objects provided by the same DataProvider.
     * 
     * @param object
     *            Object returned by the {@link #getNext()} method
     * @return the unique ID of the object
     */
    String getId(T object);

}

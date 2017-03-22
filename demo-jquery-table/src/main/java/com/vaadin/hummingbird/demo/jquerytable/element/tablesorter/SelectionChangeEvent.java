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

import com.vaadin.ui.ComponentEvent;

/**
 * Event for selection changes in {@link RichTable}s.
 * 
 * @see SelectionModel
 */
public class SelectionChangeEvent extends ComponentEvent<RichTable<?>> {

    /**
     * Constructor for the event. The actual selection can be retrieved by
     * calling {@link SelectionModel#getSelectedObjects()}.
     * 
     * @param source
     *            the table from where the event was originated.
     * @param fromClient
     *            <code>true</code> for client-side events, <code>false</code>
     *            otherwise.
     */
    public SelectionChangeEvent(RichTable<?> source, boolean fromClient) {
        super(source, fromClient);
    }

}

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
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The selection model defines how the selections are handled in
 * {@link RichTable}s.
 *
 * @param <T>
 *            the type of the data.
 * 
 * @see RichTable#setSelectionModel(SelectionModel)
 */
public class SelectionModel<T extends Serializable> implements Serializable {

    private boolean multiple;
    private Set<T> selectedObjects;

    /**
     * Default constructor.
     */
    public SelectionModel() {
        this.selectedObjects = new LinkedHashSet<>();
    }

    /**
     * Sets whether this model allows multiple selection of objects. The default
     * is <code>false</code>.
     * 
     * @param multiple
     *            <code>true</code> for multiple selection, <code>false</code>
     *            otherwise.
     */
    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    /**
     * Gets whether this model allows multiple selection of objects. The default
     * is <code>false</code>.
     * 
     * @return <code>true</code> for multiple selection, <code>false</code>
     *         otherwise.
     */
    public boolean isMultiple() {
        return multiple;
    }

    /**
     * Gets the currently selected object. If this model allows multiple
     * selection, this method returns the first selected object.
     * 
     * @return the selected object, if any.
     */
    public Optional<T> getSelectedObject() {
        return selectedObjects.isEmpty() ? Optional.empty()
                : Optional.of(selectedObjects.iterator().next());
    }

    /**
     * Gets the current Set of selected objects. The returned Set can be used
     * safely without changing the inner status of the model.
     * 
     * @return a copy of the Set of selected objects. Will return an empty Set
     *         if there are no selected objects.
     */
    public Set<T> getSelectedObjects() {
        return new LinkedHashSet<>(selectedObjects);
    }

    /*
     * Called internally by the RichTable when a client-side event is triggered.
     */
    void addOrRemoveFromSelection(T object) {
        if (selectedObjects.contains(object)) {
            selectedObjects.remove(object);
        } else {
            if (!multiple) {
                selectedObjects.clear();
            }
            selectedObjects.add(object);
        }
    }

}

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
package com.vaadin.flow.demo.jquerytable.element.tablesorter;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.function.SerializableFunction;

/**
 * Table component based on the jQuery Tablesorter plugin, available at
 * https://mottie.github.io/tablesorter/docs/
 *
 * This implementation uses the Bootstrap 4 theme, and the following widgets:
 * <ul>
 * <li>group</li>
 * <li>filter</li>
 * <li>columns</li>
 * <li>zebra</li>
 * </ul>
 *
 * @param <T>
 *            the type of the model object used with this table
 *
 */
@JavaScript("frontend://bower_components/jquery/dist/jquery.min.js")
@JavaScript("frontend://bower_components/tablesorter/dist/js/jquery.tablesorter.min.js")
@JavaScript("frontend://bower_components/tablesorter/dist/js/jquery.tablesorter.widgets.min.js")
@JavaScript("frontend://bower_components/tablesorter/dist/js/widgets/widget-grouping.min.js")
@StyleSheet("frontend://bower_components/tablesorter/dist/css/theme.bootstrap_4.min.css")
@StyleSheet("frontend://bower_components/tablesorter/dist/css/widget.grouping.min.css")
@Tag("table")
public class RichTable<T extends Serializable> extends Component
        implements ClickNotifier, HasStyle {

    private static final String DATA_ID_PROPERTY = "dataid";
    private static final String SELECTED_CSS_CLASS = "selected";

    private List<T> data = Collections.emptyList();
    private SerializableFunction<T, String> idFunction;
    private List<RichColumn<T>> columns;
    private Map<String, T> dataById;
    private Map<String, Element> rowsById;
    private T selectedObject;

    private final InnerClickListener innerClickListener = new InnerClickListener();

    /**
     * Constructor for the RichTable. Sets the function capable of getting the
     * unique identifier for a given object. This identifier should be unique,
     * not <code>null</code> and immutable among all objects provided by the
     * same RichTable.
     *
     * @param idFunction
     *            The function that can return the unique ID of a given object
     *            from the model. Can not be <code>null</code>.
     */
    public RichTable(SerializableFunction<T, String> idFunction) {
        this.idFunction = Objects.requireNonNull(idFunction,
                "The idFunction can not be null.");
        dataById = new HashMap<>();
        rowsById = new HashMap<>();
    }

    /**
     * Set the columns of the table. Each column is responsible to render its
     * values and provide its configuration options.
     *
     * @param columns
     *            The list of columns to render in the table. Must be not
     *            <code>null</code>.
     *
     * @see RichColumn
     */
    public void setColumns(List<RichColumn<T>> columns) {
        this.columns = Objects.requireNonNull(columns,
                "Null columns are not allowed.");

        Element thead = new Element("thead");
        Element tr = new Element("tr");
        for (RichColumn<T> column : columns) {
            Element th = new Element("th");
            if (column.getColumnClasses() != null) {
                th.getClassList().addAll(column.getColumnClasses());
            }
            th.setText(column.getColumnName());
            tr.appendChild(th);
        }
        thead.appendChild(tr);
        getElement().appendChild(thead);

        Element tbody = new Element("tbody");
        getElement().appendChild(tbody);
    }

    /**
     * Sets the data objects to be visible in the component. If the component is
     * already attached to the document, the table is refreshed.
     *
     * @param data
     *            The model objects.
     */
    public void setData(List<T> data) {
        this.data = data;
        updateContentIfAttached();
    }

    /**
     * Gets the data objects currently displayed by the table. The returned list
     * can not be modified.
     *
     * @return The model objects.
     */
    public List<T> getData() {
        return Collections.unmodifiableList(data);
    }

    /**
     * Updates the table content, reading the data model and rebuilding the body
     * the table. After the data is read, it notifies the client-side framework
     * of the changes, and the plugin does the sorting, filtering and grouping.
     */
    private void updateContentIfAttached() {
        if (!getUI().isPresent()) {
            return;
        }

        Optional<Element> tbodyOptional = getElement().getChildren()
                .filter(el -> "tbody".equals(el.getTag())).findFirst();
        if (!tbodyOptional.isPresent()) {
            return;
        }
        Element tbody = tbodyOptional.get();
        tbody.removeAllChildren();
        dataById.clear();
        rowsById.clear();

        for (T modelObject : data) {
            Element tr = new Element("tr");
            String objectId = idFunction.apply(modelObject);
            dataById.put(objectId, modelObject);
            rowsById.put(objectId, tr);

            tr.setProperty(DATA_ID_PROPERTY, objectId);
            tr.addEventListener("click", innerClickListener,
                    "element." + DATA_ID_PROPERTY);

            for (RichColumn<T> col : columns) {
                Element td = new Element("td");
                String model = col.getModelValue(modelObject);
                if (model != null) {
                    td.setAttribute("data-text", model);
                }
                td.setText(col.getRenderedValue(modelObject));
                tr.appendChild(td);
            }

            tbody.appendChild(tr);
        }
        getUI().get().getPage().executeJavaScript(
                "jQuery($0).trigger('update', [ true ]);", getElement());
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        if (attachEvent.isInitialAttach()) {
            if (!getUI().isPresent()) {
                throw new IllegalStateException(
                        "The component needs to be attached to an UI.");
            }

            // Initialization script. This is needed only once.
            addClassName("tablesorter");
            getUI().get().getPage().executeJavaScript(
                    "jQuery($0).tablesorter({ theme : 'bootstrap', widgets : [ 'group', 'filter', 'columns', 'zebra' ] });",
                    getElement());

            updateContentIfAttached();
        }
    }

    /**
     * Gets the selected object by the client, if any.
     *
     * @return The object referenced by the table row clicked on the client.
     */
    public Optional<T> getSelectedObject() {
        return Optional.ofNullable(selectedObject);
    }

    /**
     * Add listener to the selection change event.
     * 
     * @param listener
     *            the listener to add
     */
    public void addSelectionChangeListener(
            ComponentEventListener<SelectionChangeEvent> listener) {
        addListener(SelectionChangeEvent.class, listener);
    }

    /*
     * Private class used to translate click events from the client to
     * SelectionChangeEvents in the server.
     */
    private class InnerClickListener implements DomEventListener {
        @Override
        public void handleEvent(DomEvent event) {
            String id = event.getSource().getProperty(DATA_ID_PROPERTY);

            if (id == null) {
                return;
            }
            T object = dataById.get(id);
            if (object == null) {
                return;
            }
            T previousSelectedObject = selectedObject;

            if (previousSelectedObject != null) {
                setObjectSelected(previousSelectedObject, false);

                if (!previousSelectedObject.equals(object)) {
                    selectedObject = object;
                    setObjectSelected(object, true);
                } else {
                    selectedObject = null;
                }

            } else {
                selectedObject = object;
                setObjectSelected(object, true);
            }

            fireEvent(new SelectionChangeEvent(RichTable.this, false));
        }

        /*
         * Sets or removed the selected CSS class at the table row.
         */
        private void setObjectSelected(T object, boolean selected) {
            String key = idFunction.apply(object);
            Element tr = rowsById.get(key);
            if (tr != null) {
                if (selected) {
                    tr.getClassList().add(SELECTED_CSS_CLASS);
                } else {
                    tr.getClassList().remove(SELECTED_CSS_CLASS);
                }
            }
        }
    }
}

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.dom.DomEvent;
import com.vaadin.hummingbird.dom.DomEventListener;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.html.event.ClickNotifier;
import com.vaadin.ui.AttachEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasStyle;

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
@JavaScript("context://bower_components/jquery/dist/jquery.min.js")
@JavaScript("context://bower_components/tablesorter/dist/js/jquery.tablesorter.min.js")
@JavaScript("context://bower_components/tablesorter/dist/js/jquery.tablesorter.widgets.min.js")
@JavaScript("context://bower_components/tablesorter/dist/js/widgets/widget-grouping.min.js")
@StyleSheet("context://bower_components/tablesorter/dist/css/theme.bootstrap_4.min.css")
@StyleSheet("context://bower_components/tablesorter/dist/css/widget.grouping.min.css")
@Tag("table")
public class RichTable<T extends Serializable> extends Component
        implements ClickNotifier, HasStyle {

    private static final String DATA_ID_PROPERTY = "dataid";
    private static final String SELECTED_CSS_CLASS = "selected";

    private DataProvider<T> dataProvider;
    private List<RichColumn<T>> columns;
    private BiMap<String, T> dataById;
    private Map<String, Element> rowsById;
    private SelectionModel<T> selectionModel;
    private boolean updated;

    private final InnerClickListener innerClickListener = new InnerClickListener();

    /**
     * Default constructor.
     */
    public RichTable() {
        dataById = HashBiMap.create();
        rowsById = new HashMap<>();
    }

    /**
     * Sets the {@link DataProvider} from where the data will be fetched to be
     * rendered in the table.
     * 
     * @param dataProvider
     *            The {@link DataProvider}. Must be not null.
     */
    public void setDataProvider(DataProvider<T> dataProvider) {
        assert dataProvider != null : "The dataProvider must not be null.";
        this.dataProvider = dataProvider;
    }

    /**
     * Set the columns of the table. Each column is responsible to render its
     * values and provide its configuration options.
     * 
     * @param columns
     *            The list of columns to render in the table. Must be not null,
     *            not empty.
     * 
     * @see RichColumn
     */
    public void setColumns(List<RichColumn<T>> columns) {
        assert columns != null && columns
                .isEmpty() : "At least 1 column is needed to render the table.";
        this.columns = columns;

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
     * Updates the table content, reading the data from the {@link DataProvider}
     * and rebuilding the body the table. After the data is read, it notifies
     * the client-side framework of the changes, and the plugin does the
     * sorting, filtering and grouping.
     * 
     * Users should call this method when there are updates in the model.
     */
    public void updateContent() {
        assert getUI()
                .isPresent() : "The component needs to be attached to an UI.";
        assert getId().isPresent() : "The component needs to have an ID.";

        Optional<Element> tbodyOptional = getElement().getChildren()
                .filter(el -> "tbody".equals(el.getTag())).findFirst();
        if (!tbodyOptional.isPresent()) {
            return;
        }
        Element tbody = tbodyOptional.get();
        tbody.removeAllChildren();
        dataById.clear();
        rowsById.clear();

        Optional<T> object = dataProvider.getNext();
        while (object.isPresent()) {
            T modelObject = object.get();
            Element tr = new Element("tr");
            String objectId = dataProvider.getId(modelObject);
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
            object = dataProvider.getNext();
        }
        getUI().get().getPage().executeJavaScript(
                "$('#" + getId().get() + "').trigger('update', [ true ]);");
        updated = true;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        if (attachEvent.isInitialAttach()) {
            assert getUI()
                    .isPresent() : "The component needs to be attached to an UI.";
            assert getId().isPresent() : "The component needs to have an ID.";

            // Initialization script. This is needed only once.
            this.addClassName("tablesorter");
            getUI().get().getPage().executeJavaScript("$('#" + getId().get()
                    + "').tablesorter({ theme : 'bootstrap', widgets : [ 'group', 'filter', 'columns', 'zebra' ] });");
        }

        if (!updated) {
            updateContent();
        }
    }

    /**
     * Gets the current selection model of this RichTable, if any.
     * 
     * @return the selection model, or <code>null</code> if not present.
     */
    public SelectionModel<T> getSelectionModel() {
        return selectionModel;
    }

    /**
     * Sets the selection model used for this RichTable.
     * 
     * @param selectionModel
     *            the selection model. Use <code>null</code> to disable
     *            selection of items.
     */
    public void setSelectionModel(SelectionModel<T> selectionModel) {
        this.selectionModel = selectionModel;
    }

    /*
     * Private class used to translate click events from the client to
     * SelectionChangeEvents in the server.
     */
    private class InnerClickListener implements DomEventListener {
        @Override
        public void handleEvent(DomEvent event) {
            String id = event.getSource().getProperty(DATA_ID_PROPERTY);

            if (id == null || selectionModel == null) {
                return;
            }
            T object = dataById.get(id);
            if (object == null) {
                return;
            }
            Set<T> previousSelectedObjects = selectionModel
                    .getSelectedObjects();
            selectionModel.addOrRemoveFromSelection(object);
            Set<T> newSelectedObjects = selectionModel.getSelectedObjects();

            previousSelectedObjects.removeAll(newSelectedObjects);

            BiMap<T, String> inverse = dataById.inverse();
            previousSelectedObjects.forEach(obj -> {
                String key = inverse.get(obj);
                Element tr = rowsById.get(key);
                if (tr != null) {
                    tr.getClassList().remove(SELECTED_CSS_CLASS);
                }
            });

            newSelectedObjects.forEach(obj -> {
                String key = inverse.get(obj);
                Element tr = rowsById.get(key);
                if (tr != null) {
                    tr.getClassList().add(SELECTED_CSS_CLASS);
                }
            });

            fireEvent(new SelectionChangeEvent(RichTable.this, false));
        }
    }

}

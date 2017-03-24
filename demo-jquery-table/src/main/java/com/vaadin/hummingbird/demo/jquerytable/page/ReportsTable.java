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
package com.vaadin.hummingbird.demo.jquerytable.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.vaadin.hummingbird.demo.jquerytable.element.tablesorter.RichColumn;
import com.vaadin.hummingbird.demo.jquerytable.element.tablesorter.RichTable;
import com.vaadin.hummingbird.demo.jquerytable.persistence.Report;

import humanize.Humanize;

/**
 * Concrete class based on {@link RichTable}, that encapsulates the logic to
 * render {@link Report}s.
 *
 */
public final class ReportsTable extends RichTable<Report> {

    private static final String FILTER_SELECT = "filter-select";
    private static final String GROUP_BY_TEXT = "group-text";
    private static final String GROUP_DISABLED = "group-false";

    /**
     * Creates a new ReportsTable with the given element id.
     * 
     * @param id
     *            The id for the table element. Must be not <code>null</code>.
     */
    public ReportsTable(String id) {
        setId(Objects.requireNonNull(id,
                "The ReportsTable must have a not null ID."));

        // The column definitions. Each column is a anonymous class.

        List<RichColumn<Report>> columns = new ArrayList<>();
        columns.add(new ReportColumn().setColumnClasses(GROUP_DISABLED)
                .setColumnName("ID").setRenderedValueFunction(
                        object -> String.valueOf(object.getId())));

        columns.add(new ReportColumn()
                .setColumnClasses(FILTER_SELECT, GROUP_BY_TEXT)
                .setColumnName("Priority").setRenderedValueFunction(
                        object -> object.getPriority().name()));

        columns.add(new ReportColumn()
                .setColumnClasses(FILTER_SELECT, GROUP_BY_TEXT)
                .setColumnName("Type")
                .setRenderedValueFunction(object -> object.getType().name()));

        columns.add(new ReportColumn().setColumnClasses(GROUP_DISABLED)
                .setColumnName("Summary")
                .setRenderedValueFunction(Report::getSummary));

        columns.add(new ReportColumn()
                .setColumnClasses(FILTER_SELECT, GROUP_BY_TEXT)
                .setColumnName("Assigned to")
                .setRenderedValueFunction(Report::getAssignedTo));

        columns.add(new ReportColumn().setColumnClasses(GROUP_DISABLED)
                .setColumnName("Last modified")
                .setRenderedValueFunction(object -> Humanize
                        .naturalTime(object.getLastModified()))
                .setModelValueFunction(object -> String
                        .valueOf(object.getLastModified().getTime())));

        columns.add(new ReportColumn().setColumnClasses(GROUP_DISABLED)
                .setColumnName("Reported")
                .setRenderedValueFunction(
                        object -> Humanize.naturalTime(object.getReported()))
                .setModelValueFunction(object -> String
                        .valueOf(object.getReported().getTime())));

        // sets the list of columns to the table
        setColumns(columns);
    }

    /*
     * Helper class to provide a fluent interface to create columns for reports
     */
    private class ReportColumn implements RichColumn<Report> {

        private Function<Report, String> renderedValueFunction;
        private Function<Report, String> modelValueFunction;
        private List<String> columnClasses;
        private String columnName;

        public ReportColumn setColumnName(String columnName) {
            this.columnName = columnName;
            return this;
        }

        public ReportColumn setColumnClasses(String... columnClasses) {
            this.columnClasses = Arrays.asList(columnClasses);
            return this;
        }

        public ReportColumn setModelValueFunction(
                Function<Report, String> modelValueFunction) {
            this.modelValueFunction = modelValueFunction;
            return this;
        }

        public ReportColumn setRenderedValueFunction(
                Function<Report, String> renderedValueFunction) {
            this.renderedValueFunction = renderedValueFunction;
            return this;
        }

        @Override
        public String getColumnName() {
            return columnName;
        }

        @Override
        public List<String> getColumnClasses() {
            return columnClasses;
        }

        @Override
        public String getModelValue(Report object) {
            return modelValueFunction == null ? null
                    : modelValueFunction.apply(object);
        }

        @Override
        public String getRenderedValue(Report object) {
            return renderedValueFunction == null ? null
                    : renderedValueFunction.apply(object);
        }
    }

}

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
package com.vaadin.hummingbird.demo.jquerytable.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.vaadin.bugrap.domain.entities.Report;

import com.vaadin.hummingbird.demo.jquerytable.element.tablesorter.RichColumn;
import com.vaadin.hummingbird.demo.jquerytable.element.tablesorter.RichTable;

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
     *            The id for the table element. Must be not null and not empty.
     */
    public ReportsTable(String id) {
        assert id != null && !id
                .isEmpty() : "The ReportsTable must have a not null, not empty ID.";
        setId(id);

        List<RichColumn<Report>> columns = new ArrayList<>();
        columns.add(new RichColumn<Report>() {

            @Override
            public String getRenderedValue(Report object) {
                return String.valueOf(object.getId());
            }

            @Override
            public String getModelValue(Report object) {
                return null;
            }

            @Override
            public List<String> getColumnClasses() {
                return Arrays.asList(GROUP_DISABLED);
            }

            @Override
            public String getColumnName() {
                return "ID";
            }
        });

        columns.add(new RichColumn<Report>() {

            @Override
            public String getRenderedValue(Report object) {
                return object.getPriority().name();
            }

            @Override
            public String getModelValue(Report object) {
                return null;
            }

            @Override
            public List<String> getColumnClasses() {
                return Arrays.asList(FILTER_SELECT, GROUP_BY_TEXT);
            }

            @Override
            public String getColumnName() {
                return "Priority";
            }
        });

        columns.add(new RichColumn<Report>() {

            @Override
            public String getRenderedValue(Report object) {
                return object.getType() == null ? "" : object.getType().name();
            }

            @Override
            public String getModelValue(Report object) {
                return null;
            }

            @Override
            public List<String> getColumnClasses() {
                return Arrays.asList(FILTER_SELECT, GROUP_BY_TEXT);
            }

            @Override
            public String getColumnName() {
                return "Type";
            }
        });

        columns.add(new RichColumn<Report>() {

            @Override
            public String getRenderedValue(Report object) {
                return object.getSummary();
            }

            @Override
            public String getModelValue(Report object) {
                return null;
            }

            @Override
            public List<String> getColumnClasses() {
                return Arrays.asList(GROUP_DISABLED);
            }

            @Override
            public String getColumnName() {
                return "Summary";
            }
        });

        columns.add(new RichColumn<Report>() {

            @Override
            public String getRenderedValue(Report object) {
                return object.getAssigned() == null ? ""
                        : object.getAssigned().getName();
            }

            @Override
            public String getModelValue(Report object) {
                return null;
            }

            @Override
            public List<String> getColumnClasses() {
                return Arrays.asList(FILTER_SELECT, GROUP_BY_TEXT);
            }

            @Override
            public String getColumnName() {
                return "Assigned to";
            }
        });

        columns.add(new RichColumn<Report>() {

            @Override
            public String getRenderedValue(Report object) {
                return object.getTimestamp() == null ? ""
                        : Humanize.naturalTime(object.getTimestamp());
            }

            @Override
            public String getModelValue(Report object) {
                return object.getTimestamp() == null ? ""
                        : String.valueOf(object.getTimestamp().getTime());
            }

            @Override
            public List<String> getColumnClasses() {
                return Arrays.asList(GROUP_DISABLED);
            }

            @Override
            public String getColumnName() {
                return "Last modified";
            }
        });

        columns.add(new RichColumn<Report>() {

            @Override
            public String getRenderedValue(Report object) {
                return object.getReportedTimestamp() == null ? ""
                        : Humanize.naturalTime(object.getReportedTimestamp());
            }

            @Override
            public String getModelValue(Report object) {
                return object.getReportedTimestamp() == null ? ""
                        : String.valueOf(
                                object.getReportedTimestamp().getTime());
            }

            @Override
            public List<String> getColumnClasses() {
                return Arrays.asList(GROUP_DISABLED);
            }

            @Override
            public String getColumnName() {
                return "Reported";
            }
        });

        setColumns(columns);
    }

}

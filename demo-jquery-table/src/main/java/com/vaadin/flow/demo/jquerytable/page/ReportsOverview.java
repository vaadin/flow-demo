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
package com.vaadin.flow.demo.jquerytable.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Title;
import com.vaadin.flow.demo.jquerytable.element.html.Option;
import com.vaadin.flow.demo.jquerytable.element.html.Select;
import com.vaadin.flow.demo.jquerytable.element.tablesorter.RichTable;
import com.vaadin.flow.demo.jquerytable.element.tablesorter.SelectionChangeEvent;
import com.vaadin.flow.demo.jquerytable.persistence.IssuesRepository;
import com.vaadin.flow.demo.jquerytable.persistence.Project;
import com.vaadin.flow.demo.jquerytable.persistence.ProjectVersion;
import com.vaadin.flow.demo.jquerytable.persistence.Report;
import com.vaadin.flow.html.Div;
import com.vaadin.flow.html.HtmlComponent;
import com.vaadin.flow.html.HtmlContainer;
import com.vaadin.flow.html.Label;
import com.vaadin.flow.html.Span;
import com.vaadin.flow.router.View;

/**
 * The one and only view in the jQuery demo application.
 */
@Title("Reports overview")
@StyleSheet("frontend://css/site.css")
public class ReportsOverview extends Div implements View {

    private static final String ALL_VERSIONS_KEY = "all-versions";

    private final IssuesRepository repository = IssuesRepository.get();
    private final Option allVersions;

    private Select projectName;
    private Select projectVersion;
    private Span numberOfReports;
    private RichTable<Report> table;
    private Div selectedReport;

    /**
     * Initializes the view. Invoked by the framework when needed.
     */
    public ReportsOverview() {
        add(createHeader(), createMainContent(), createSelectionContent());

        allVersions = new Option();
        allVersions.setText("All versions");
        allVersions.setValue(ALL_VERSIONS_KEY);

        projectName.addChangeListener(evt -> {
            Project selectedProject = getSelectedProject();
            numberOfReports.setText(
                    String.valueOf(repository.countReports(selectedProject)));

            projectVersion.getElement().removeAllChildren();
            getProjectVersionOptions().forEach(projectVersion::addOption);

            table.setData(repository.findReports(getSelectedProject(),
                    getSelectedProjectVersion()));
        });

        projectVersion.addChangeListener(evt -> {
            table.setData(repository.findReports(getSelectedProject(),
                    getSelectedProjectVersion()));
        });
    }

    /**
     * Creates and gets the header part of the application.
     */
    private HtmlComponent createHeader() {
        HtmlContainer header = new HtmlContainer("header");

        projectName = new Select();
        projectName.setId("projectName");
        getProjectOptions().forEach(projectName::addOption);

        numberOfReports = new Span();
        numberOfReports.setClassName("number-of-reports");
        numberOfReports.setText(
                String.valueOf(repository.countReports(getSelectedProject())));

        projectVersion = new Select();
        projectVersion.setId("project-versions");
        getProjectVersionOptions().forEach(projectVersion::addOption);

        Label label = new Label();
        label.setText("Reports for");
        label.setFor(projectVersion);

        header.add(projectName, numberOfReports, label, projectVersion);
        return header;
    }

    /**
     * Gets the selected project from the select at the header.
     */
    private Project getSelectedProject() {
        Optional<String> selectedValue = projectName.getSelectedValue();
        if (selectedValue.isPresent()) {
            long projectId = Long.parseLong(selectedValue.get());
            Optional<Project> project = repository.findProjects().stream()
                    .filter(p -> p.getId() == projectId).findFirst();
            if (project.isPresent()) {
                return project.get();
            }
        }

        // in this demo is guaranteed that there is at least one project in the
        // database
        return repository.findProjects().stream().sorted(
                (p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()))
                .findFirst().get();
    }

    /**
     * Gets the selected project version from the select at the header.
     */
    private ProjectVersion getSelectedProjectVersion() {
        Optional<String> selectedValue = projectVersion.getSelectedValue();
        if (!selectedValue.isPresent()
                || ALL_VERSIONS_KEY.equals(selectedValue.get())) {
            return null;
        }
        long versionId = Long.parseLong(selectedValue.get());
        return repository.findProjectVersions(getSelectedProject()).stream()
                .filter(version -> versionId == version.getId()).findFirst()
                .orElse(null);
    }

    /**
     * Creates and gets the available project options to be inserted at the
     * select at the header.
     */
    private List<Option> getProjectOptions() {
        List<Project> projects = repository.findProjects();
        return projects.stream().sorted().map(project -> {
            Option opt = new Option();
            opt.setText(project.getName());
            opt.setValue(String.valueOf(project.getId()));
            return opt;
        }).collect(Collectors.toList());
    }

    /**
     * Creates and gets the available project versions based on the selection of
     * the project.
     */
    private List<Option> getProjectVersionOptions() {
        Project project = getSelectedProject();
        List<ProjectVersion> projectVersions = repository
                .findProjectVersions(project);

        List<Option> options = new ArrayList<>(projectVersions.size() + 1);
        options.add(allVersions);

        projectVersions.stream().sorted().forEach(version -> {
            Option opt = new Option();
            opt.setText(version.getName());
            opt.setValue(String.valueOf(version.getId()));
            options.add(opt);
        });

        return options;
    }

    /**
     * Gets the main content, with the {@link RichTable} inside.
     */
    private HtmlContainer createMainContent() {
        HtmlContainer mainContent = new HtmlContainer("main");

        table = new ReportsTable();
        table.setData(repository.findReports(getSelectedProject(),
                getSelectedProjectVersion()));

        mainContent.add(table);

        // SelectionChangeEvent listener that shows a message on screen when
        // something is selected
        table.addListener(SelectionChangeEvent.class, evt -> {
            Optional<Report> selectedObject = table.getSelectedObject();
            if (selectedObject.isPresent()) {
                selectedReport.setClassName("selected-report");
                selectedReport.setText("You selected the report #"
                        + selectedObject.get().getId());
            } else {
                selectedReport.setText("");
                selectedReport.removeClassName("selected-report");
            }
        });

        return mainContent;
    }

    /**
     * Gets the section where the selection message is shown.
     */
    private HtmlContainer createSelectionContent() {
        HtmlContainer section = new HtmlContainer("section");

        selectedReport = new Div();
        section.add(selectedReport);

        return section;
    }

}

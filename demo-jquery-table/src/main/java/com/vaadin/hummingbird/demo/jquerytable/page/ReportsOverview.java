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
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.vaadin.bugrap.domain.BugrapRepository;
import org.vaadin.bugrap.domain.BugrapRepository.ReportsQuery;
import org.vaadin.bugrap.domain.entities.Project;
import org.vaadin.bugrap.domain.entities.ProjectVersion;
import org.vaadin.bugrap.domain.entities.Report;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Title;
import com.vaadin.hummingbird.demo.jquerytable.element.html5.Header;
import com.vaadin.hummingbird.demo.jquerytable.element.html5.Main;
import com.vaadin.hummingbird.demo.jquerytable.element.html5.Option;
import com.vaadin.hummingbird.demo.jquerytable.element.html5.Select;
import com.vaadin.hummingbird.demo.jquerytable.element.tablesorter.ListDataProvider;
import com.vaadin.hummingbird.demo.jquerytable.element.tablesorter.RichTable;
import com.vaadin.hummingbird.demo.jquerytable.persistence.BugrapPersistence;
import com.vaadin.hummingbird.html.Div;
import com.vaadin.hummingbird.html.Label;
import com.vaadin.hummingbird.html.Span;
import com.vaadin.hummingbird.router.View;

/**
 * The one and only view in the jQuery demo application.
 */
@Title("Reports overview")
@StyleSheet("css/site.css")
public class ReportsOverview extends Div implements View {

    private static final String ALL_VERSIONS_KEY = "all-versions";

    private final BugrapRepository repository = BugrapPersistence
            .getRepository();
    private Select projectName;
    private Select projectVersion;
    private Span numberOfReports;
    private ListDataProvider<Report> dataProvider;
    private RichTable<Report> table;

    /**
     * Initializes the view. Invoked by the framework when needed.
     */
    public ReportsOverview() {
        this.add(getHeader(), getMainContent());

        projectName.addChangeListener(evt -> {
            Project selectedProject = getSelectedProject();
            numberOfReports.setText(
                    String.valueOf(repository.countReports(selectedProject)));

            projectVersion.getElement().removeAllChildren();
            getProjectVersionOptions().forEach(projectVersion::addOption);

            dataProvider.setData(new ArrayList<>(
                    repository.findReports(buildReportsQuery())));
            table.updateContent();
        });

        projectVersion.addChangeListener(evt -> {
            dataProvider.setData(new ArrayList<>(
                    repository.findReports(buildReportsQuery())));
            table.updateContent();
        });
    }

    /**
     * Creates and gets the header part of the application
     */
    private Header getHeader() {
        Header header = new Header();

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
        Optional<ProjectVersion> v = repository
                .findProjectVersions(getSelectedProject()).stream()
                .filter(version -> versionId == version.getId()).findFirst();
        return v.orElse(null);
    }

    /**
     * Creates and gets the available project options to be inserted at the
     * select at the header.
     */
    private List<Option> getProjectOptions() {
        List<Option> options = new ArrayList<>();

        Set<Project> projects = repository.findProjects();
        projects.stream().sorted().forEach(project -> {
            Option opt = new Option();
            opt.setText(project.getName());
            opt.setValue(String.valueOf(project.getId()));
            options.add(opt);
        });

        return options;
    }

    /**
     * Creates and gets the available project versions based on the selection of
     * the project.
     */
    private List<Option> getProjectVersionOptions() {
        List<Option> options = new ArrayList<>();

        Project project = getSelectedProject();

        Option all = new Option();
        all.setText("All versions");
        all.setValue(ALL_VERSIONS_KEY);
        options.add(all);

        Set<ProjectVersion> projectVersions = repository
                .findProjectVersions(project);
        projectVersions.stream().sorted().forEach(version -> {
            Option opt = new Option();
            opt.setText(version.getVersion());
            opt.setValue(String.valueOf(version.getId()));
            options.add(opt);
        });

        return options;
    }

    /**
     * Gets the main content, with th {@link RichTable} inside.
     */
    private Main getMainContent() {
        Main mainContent = new Main();

        table = new ReportsTable("main-table");
        table.setDataProvider(dataProvider = new ListDataProvider<>());
        dataProvider.setData(
                new ArrayList<>(repository.findReports(buildReportsQuery())));

        mainContent.add(table);
        return mainContent;
    }

    /**
     * Builds and gets the {@link ReportsQuery} based on the selections in the
     * UI.
     */
    private ReportsQuery buildReportsQuery() {
        ReportsQuery query = new ReportsQuery();
        query.project = getSelectedProject();
        query.projectVersion = getSelectedProjectVersion();
        return query;
    }
}

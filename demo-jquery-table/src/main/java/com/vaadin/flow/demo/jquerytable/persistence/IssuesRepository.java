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
package com.vaadin.flow.demo.jquerytable.persistence;

import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Repository class for {@link Project}, {@link ProjectVersion} and
 * {@link Report}, that creates an in-memory, random generated database.
 */
public final class IssuesRepository implements Serializable {

    private static final IssuesRepository INSTANCE = new IssuesRepository(42);

    private List<Project> projects;
    private Map<Project, List<ProjectVersion>> versionsByProject;
    private Map<ProjectVersion, List<Report>> reportsByVersion;

    /*
     * Internal constructor. Creates the database using random values.
     * 
     * @param seed The seed of the random number generator.
     */
    private IssuesRepository(int seed) {
        Random random = new Random(seed);
        Instant now = Instant.now();

        int idCounter = 1;
        // at least 2 projects (2 - 10)
        int numOfProjects = random.nextInt(9) + 2;

        projects = new ArrayList<>(numOfProjects);
        versionsByProject = new HashMap<>();
        reportsByVersion = new HashMap<>();

        for (int i = 0; i < numOfProjects; i++) {
            Project project = generateProject(idCounter++, i);
            projects.add(project);

            // at least 2 versions per project (2 - 10)
            int numOfVersions = random.nextInt(9) + 2;
            List<ProjectVersion> versions = new ArrayList<>(numOfVersions);
            versionsByProject.put(project, versions);

            for (int j = 0; j < numOfVersions; j++) {
                ProjectVersion version = generateProjectVersion(idCounter++, j,
                        project);
                versions.add(version);

                // at least 2 reports per version (2 - 30)
                int numOfReports = random.nextInt(29) + 2;
                List<Report> reports = new ArrayList<>(numOfReports);
                reportsByVersion.put(version, reports);

                for (int k = 0; k < numOfReports; k++) {
                    Report report = generateReport(idCounter++, k, version, now,
                            random);
                    reports.add(report);
                }

                // shuffles the reports for the sorting at the client-side to be
                // visible
                Collections.shuffle(reports, random);
            }
        }
    }

    private Project generateProject(int id, int index) {
        Project project = new Project();
        project.setId(id);
        project.setName("Project " + (index + 1));
        return project;
    }

    private ProjectVersion generateProjectVersion(int id, int index,
            Project project) {
        ProjectVersion version = new ProjectVersion();
        version.setId(id);
        version.setName("Version " + (index + 1));
        version.setProject(project);
        return version;
    }

    private Report generateReport(int id, int index, ProjectVersion version,
            Instant baseDate, Random random) {

        Report report = new Report();
        report.setId(id);
        report.setAssignedTo(random.nextBoolean() ? "Developer" : null);
        report.setPriority(IssuePriority.values()[random
                .nextInt(IssuePriority.values().length)]);
        report.setType(
                IssueType.values()[random.nextInt(IssueType.values().length)]);
        report.setVersion(version);

        report.setSummary(report.getType() == IssueType.FEATURE
                ? ("Feature request " + (index + 1))
                : ("Bug report " + (index + 1)));

        Instant lastModified = baseDate
                .minus(Period.ofDays(random.nextInt(30)));
        Instant reported = lastModified
                .minus(Period.ofDays(random.nextInt(30)));

        report.setReported(Date.from(reported));
        report.setLastModified(Date.from(lastModified));
        return report;
    }

    /**
     * Gets all projects from the database.
     * 
     * @return the projects.
     */
    public List<Project> findProjects() {
        return new ArrayList<>(projects);
    }

    /**
     * Gets all {@link ProjectVersion}s of a given {@link Project}.
     * 
     * @param project
     *            the project.
     * @return the project versions.
     */
    public List<ProjectVersion> findProjectVersions(Project project) {
        List<ProjectVersion> versions = versionsByProject.get(project);
        if (versions == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(versions);
    }

    /**
     * Counts the number of {@link Report}s registered by a given
     * {@link Project}.
     * 
     * @param project
     *            the project.
     * @return the number of reports of the project.
     */
    public int countReports(Project project) {
        List<ProjectVersion> versions = versionsByProject.get(project);
        if (versions == null || versions.isEmpty()) {
            return 0;
        }
        return versions.stream()
                .map(version -> reportsByVersion.get(version).size())
                .collect(Collectors.summingInt(Integer::intValue));
    }

    /**
     * Gets all {@link Report}s of a {@link Project} and {@link ProjectVersion}.
     * When the version is <code>null</code>, all {@link Report} of all versions
     * of the project are returned.
     * 
     * @param project
     *            the project, not <code>null</code>.
     * @param version
     *            the version. When <code>null</code>, all versions of the
     *            project are considered in the search.
     * @return all matched reports.
     */
    public List<Report> findReports(Project project, ProjectVersion version) {
        Objects.requireNonNull(project);
        if (version == null) {
            List<ProjectVersion> versions = versionsByProject.get(project);
            if (versions == null) {
                return Collections.emptyList();
            }
            List<Report> all = new ArrayList<>();
            versions.stream().flatMap(v -> findReports(v).stream())
                    .forEachOrdered(all::add);
            return all;
        }

        return findReports(version);
    }

    /*
     * Internal method to retrieve the reports of a version.
     */
    private List<Report> findReports(ProjectVersion version) {
        List<Report> reports = reportsByVersion.get(version);
        if (reports == null) {
            return Collections.emptyList();
        }
        return reports;
    }

    /**
     * Gets the singleton instance of this repository.
     * 
     * @return the singleton instance.
     */
    public static IssuesRepository get() {
        return INSTANCE;
    }

}

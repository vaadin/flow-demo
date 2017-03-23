package com.vaadin.hummingbird.demo.persistence;

import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.vaadin.bugrap.domain.BugrapRepository.ReportsQuery;
import org.vaadin.bugrap.domain.entities.Project;
import org.vaadin.bugrap.domain.entities.ProjectVersion;
import org.vaadin.bugrap.domain.entities.Report;

import com.vaadin.hummingbird.demo.jquerytable.persistence.BugrapPersistence;

/**
 * The purpose of this test is not to test extensively the mock database, but to
 * make sure that there are sufficient data for the demo to run properly.
 */
public class BugrapPersistenceTest {

    @BeforeClass
    public static void setup() {
        BugrapPersistence.connect();
    }

    /*
     * This test ensures that the database has at least 1 project.
     */
    @Test
    public void testFindProjects() {
        Set<Project> projects = BugrapPersistence.getRepository()
                .findProjects();
        Assert.assertNotNull(projects);
        Assert.assertFalse(projects.isEmpty());
    }

    /*
     * This test ensures that there is at least 1 version for the first project.
     */
    @Test
    public void testFindVersions() {
        Set<Project> projects = BugrapPersistence.getRepository()
                .findProjects();
        Project project = projects.stream().sorted().findFirst().get();
        Set<ProjectVersion> versions = BugrapPersistence.getRepository()
                .findProjectVersions(project);
        Assert.assertNotNull(versions);
        Assert.assertFalse(versions.isEmpty());
    }

    /*
     * This test ensures that there are at least 2 reports for the first
     * project.
     */
    @Test
    public void testFindReports() {
        Set<Project> projects = BugrapPersistence.getRepository()
                .findProjects();
        Project project = projects.stream().sorted().findFirst().get();
        ReportsQuery query = new ReportsQuery();
        query.project = project;

        Set<Report> reports = BugrapPersistence.getRepository()
                .findReports(query);
        Assert.assertNotNull(reports);
        Assert.assertTrue(reports.size() > 1);
    }

}

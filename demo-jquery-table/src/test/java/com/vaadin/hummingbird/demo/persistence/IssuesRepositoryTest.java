package com.vaadin.hummingbird.demo.persistence;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vaadin.hummingbird.demo.jquerytable.persistence.IssuesRepository;
import com.vaadin.hummingbird.demo.jquerytable.persistence.Project;
import com.vaadin.hummingbird.demo.jquerytable.persistence.ProjectVersion;
import com.vaadin.hummingbird.demo.jquerytable.persistence.Report;

/**
 * The purpose of this test is not to test extensively the database, but to make
 * sure that there are sufficient data for the demo to run properly.
 */
public class IssuesRepositoryTest {

    private IssuesRepository repository;

    @Before
    public void setup() {
        repository = IssuesRepository.get();
    }

    /*
     * This test ensures that the database has at least 1 project.
     */
    @Test
    public void testFindProjects() {
        List<Project> projects = repository.findProjects();
        Assert.assertNotNull(projects);
        Assert.assertFalse(projects.isEmpty());
    }

    /*
     * This test ensures that there is at least 1 version for the first project.
     */
    @Test
    public void testFindVersions() {
        List<Project> projects = repository.findProjects();
        Project project = projects.stream().sorted().findFirst().get();
        List<ProjectVersion> versions = repository.findProjectVersions(project);
        Assert.assertNotNull(versions);
        Assert.assertFalse(versions.isEmpty());
    }

    /*
     * This test ensures that there are at least 2 reports for the first
     * project.
     */
    @Test
    public void testFindReports() {
        List<Project> projects = repository.findProjects();
        Project project = projects.stream().sorted().findFirst().get();

        List<Report> reports = repository.findReports(project, null);
        Assert.assertNotNull(reports);
        Assert.assertTrue(reports.size() > 1);
    }

}

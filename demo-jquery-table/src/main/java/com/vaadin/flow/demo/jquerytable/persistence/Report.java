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

import java.util.Date;

/**
 * Entity that represents a bug report or feature request in the database.
 */
public class Report extends BaseEntity {

    private ProjectVersion version;
    private IssuePriority priority;
    private IssueType type;
    private String summary;
    private String assignedTo;
    private Date lastModified;
    private Date reported;

    /**
     * Gets the {@link ProjectVersion} of this report.
     * 
     * @return the version
     */
    public ProjectVersion getVersion() {
        return version;
    }

    /**
     * Sets the {@link ProjectVersion} of this report.
     * 
     * @param version
     *            the version to set
     */
    public void setVersion(ProjectVersion version) {
        this.version = version;
    }

    /**
     * Gets the {@link IssuePriority} of this report.
     * 
     * @return the priority
     */
    public IssuePriority getPriority() {
        return priority;
    }

    /**
     * Sets the {@link IssuePriority} of this report.
     * 
     * @param priority
     *            the priority to set
     */
    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    /**
     * Gets the {@link IssueType} of this report.
     * 
     * @return the type
     */
    public IssueType getType() {
        return type;
    }

    /**
     * Sets the {@link IssueType} of this report.
     * 
     * @param type
     *            the type to set
     */
    public void setType(IssueType type) {
        this.type = type;
    }

    /**
     * Gets the summary text of this report.
     * 
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the summary text of this report.
     * 
     * @param summary
     *            the summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Gets the name of the person who is assigned to this report.
     * 
     * @return the assignedTo
     */
    public String getAssignedTo() {
        return assignedTo;
    }

    /**
     * Sets the name of the person who is assigned to this report.
     * 
     * @param assignedTo
     *            the assignedTo to set
     */
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    /**
     * Gets the {@link Date} when this report was last modified.
     * 
     * @return the lastModified
     */
    public Date getLastModified() {
        return lastModified;
    }

    /**
     * 
     * Sets the {@link Date} when this report was last modified.
     * 
     * @param lastModified
     *            the lastModified to set
     */
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * Gets the {@link Date} when this report was created.
     * 
     * @return the reported
     */
    public Date getReported() {
        return reported;
    }

    /**
     * Sets the {@link Date} when this report was created.
     * 
     * @param reported
     *            the reported to set
     */
    public void setReported(Date reported) {
        this.reported = reported;
    }
}

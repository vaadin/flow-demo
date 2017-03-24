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
package com.vaadin.hummingbird.demo.jquerytable.persistence;

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
     * @return the version
     */
    public ProjectVersion getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(ProjectVersion version) {
        this.version = version;
    }

    /**
     * @return the priority
     */
    public IssuePriority getPriority() {
        return priority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    /**
     * @return the type
     */
    public IssueType getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(IssueType type) {
        this.type = type;
    }

    /**
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary
     *            the summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return the assignedTo
     */
    public String getAssignedTo() {
        return assignedTo;
    }

    /**
     * @param assignedTo
     *            the assignedTo to set
     */
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    /**
     * @return the lastModified
     */
    public Date getLastModified() {
        return lastModified;
    }

    /**
     * @param lastModified
     *            the lastModified to set
     */
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * @return the reported
     */
    public Date getReported() {
        return reported;
    }

    /**
     * @param reported
     *            the reported to set
     */
    public void setReported(Date reported) {
        this.reported = reported;
    }
}

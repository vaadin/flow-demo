package com.vaadin.hummingbird.complexform.model.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The persistent class for the ORG_HIERARCHY database table.
 *
 */
@SuppressWarnings("serial")
public class OrgHierarchy extends ScorecardEntity {

    private long id;

    private String entityName;

    private String entityOwnerFname;

    private String entityOwnerLname;

    private String entityOwnerRacf;

    private Long entityParentId = 0L;

    // bi-directional many-to-one association to Goal
    private Set<Goal> goals;

    // bi-directional many-to-one association to GoalsAssignment
    // private Set<GoalsAssignment> goalsAssignments;

    public OrgHierarchy() {
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityOwnerFname() {
        return entityOwnerFname;
    }

    public void setEntityOwnerFname(String entityOwnerFname) {
        this.entityOwnerFname = entityOwnerFname;
    }

    public String getEntityOwnerLname() {
        return entityOwnerLname;
    }

    public void setEntityOwnerLname(String entityOwnerLname) {
        this.entityOwnerLname = entityOwnerLname;
    }

    public String getEntityOwnerRacf() {
        return entityOwnerRacf;
    }

    public void setEntityOwnerRacf(String entityOwnerRacf) {
        this.entityOwnerRacf = entityOwnerRacf;
    }

    public Long getEntityParentId() {
        return entityParentId;
    }

    public void setEntityParentId(Long entityParentId) {
        this.entityParentId = entityParentId;
    }

    public Set<Goal> getGoals() {
        if (goals == null) {
            goals = new HashSet<>();
        }
        return goals;
    }

    public void setGoals(Set<Goal> goals) {
        this.goals = goals;
    }

    public Goal addGoal(Goal goal) {
        getGoals().add(goal);
        goal.setOrgHierarchy(this);

        return goal;
    }

    public Goal removeGoal(Goal goal) {
        getGoals().remove(goal);
        goal.setOrgHierarchy(null);

        return goal;
    }

    // public Set<GoalsAssignment> getGoalsAssignments() {
    // return goalsAssignments;
    // }
    //
    // public void setGoalsAssignments(Set<GoalsAssignment> goalsAssignments) {
    // this.goalsAssignments = goalsAssignments;
    // }
    //
    // public GoalsAssignment addGoalsAssignment(GoalsAssignment
    // goalsAssignment) {
    // getGoalsAssignments().add(goalsAssignment);
    // goalsAssignment.setOrgHierarchy(this);
    //
    // return goalsAssignment;
    // }
    //
    // public GoalsAssignment removeGoalsAssignment(
    // GoalsAssignment goalsAssignment) {
    // getGoalsAssignments().remove(goalsAssignment);
    // goalsAssignment.setOrgHierarchy(null);
    //
    // return goalsAssignment;
    // }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OrgHierarchy && obj.getClass().equals(getClass())) {
            OrgHierarchy another = (OrgHierarchy) obj;
            return id == another.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(id);
        return hash;
    }
}
package com.vaadin.hummingbird.complexform.model.entity;

import java.beans.Transient;
import java.util.Date;
import java.util.Set;

import com.vaadin.hummingbird.complexform.model.helpers.ScorecardUtil;

/**
 * The persistent class for the GOALS database table.
 *
 */
@SuppressWarnings("serial")
public class Goal extends ScorecardEntity {

    private long goalId;

    private boolean cascade;

    private String name;

    private Date endDate;

    private String frequency = "Q";

    private String tactics;

    private Date startDate;

    private boolean active = true;

    private boolean frozenAssigned;

    // bi-directional many-to-one association to OrgHierarchy
    private OrgHierarchy orgHierarchy;

    // bi-directional many-to-one association to GoalsAssignment
    // private Set<GoalsAssignment> goalsAssignments;

    // bi-directional many-to-one association to Kpi
    private Set<Kpi> kpis;

    public Goal() {
    }

    public long getGoalId() {
        return goalId;
    }

    public void setGoalId(long goalId) {
        this.goalId = goalId;
    }

    public boolean getCascade() {
        return cascade;
    }

    public void setCascade(boolean cascade) {
        this.cascade = cascade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date goalEndDt) {
        endDate = goalEndDt;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String goalFreq) {
        frequency = goalFreq;
    }

    public String getTactics() {
        return tactics;
    }

    public void setTactics(String goalHow) {
        tactics = goalHow;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date goalStartDt) {
        startDate = goalStartDt;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean isActive) {
        active = isActive;
    }

    public boolean isFrozenAssigned() {
        return frozenAssigned;
    }

    public void setFrozenAssigned(boolean frozenAssigned) {
        this.frozenAssigned = frozenAssigned;
    }

    public OrgHierarchy getOrgHierarchy() {
        return orgHierarchy;
    }

    public void setOrgHierarchy(OrgHierarchy orgHierarchy) {
        this.orgHierarchy = orgHierarchy;
    }

    // public Set<GoalsAssignment> getGoalsAssignments() {
    // if (goalsAssignments == null) {
    // goalsAssignments = new HashSet<>();
    // }
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
    // goalsAssignment.setGoal(this);
    //
    // return goalsAssignment;
    // }
    //
    // public GoalsAssignment removeGoalsAssignment(
    // GoalsAssignment goalsAssignment) {
    // getGoalsAssignments().remove(goalsAssignment);
    // goalsAssignment.setGoal(null);
    //
    // return goalsAssignment;
    // }

    public Set<Kpi> getKpis() {
        return kpis;
    }

    public void setKpis(Set<Kpi> kpis) {
        this.kpis = kpis;
    }

    public Kpi addKpi(Kpi kpi) {
        getKpis().add(kpi);
        kpi.setGoal(this);

        return kpi;
    }

    public Kpi removeKpi(Kpi kpi) {
        getKpis().remove(kpi);
        kpi.setGoal(null);

        return kpi;
    }

    @Transient
    public String getPrettyStartDate() {
        return ScorecardUtil.buildPrettyString(startDate, frequency);
    }

    @Transient
    public String getPrettyEndDate() {
        return ScorecardUtil.buildPrettyString(endDate, frequency);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (goalId ^ (goalId >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Goal other = (Goal) obj;
        if (goalId != other.goalId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Goal [goalId=" + goalId + ", cascade=" + cascade + ", name="
                + name + ", endDate=" + endDate + ", frequency=" + frequency
                + ", tactics=" + tactics + ", startDate=" + startDate
                + ", active=" + active + ", frozenAndAssigned=" + frozenAssigned
                + ", orgHierarchy=" + orgHierarchy + "]";
    }

}
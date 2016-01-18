package com.vaadin.hummingbird.complexform.model.entity;

import java.util.Date;

/**
 * The persistent class for the GOAL_PERIOD database table.
 *
 */
@SuppressWarnings("serial")
public class GoalPeriod extends ScorecardEntity
        implements Cloneable, Comparable<GoalPeriod> {

    private String periodDesc;

    private Date periodKey;

    private String periodType;

    public GoalPeriod() {
    }

    public String getPeriodDesc() {
        return periodDesc;
    }

    public void setPeriodDesc(String periodDesc) {
        this.periodDesc = periodDesc;
    }

    public Date getPeriodKey() {
        return periodKey;
    }

    public void setPeriodKey(Date periodKey) {
        this.periodKey = periodKey;
    }

    public String getPeriodType() {
        return periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((periodDesc == null) ? 0 : periodDesc.hashCode());
        result = prime * result
                + ((periodKey == null) ? 0 : periodKey.hashCode());
        result = prime * result
                + ((periodType == null) ? 0 : periodType.hashCode());
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
        GoalPeriod other = (GoalPeriod) obj;
        if (periodDesc == null) {
            if (other.periodDesc != null) {
                return false;
            }
        } else if (!periodDesc.equals(other.periodDesc)) {
            return false;
        }
        if (periodKey == null) {
            if (other.periodKey != null) {
                return false;
            }
        } else if (!periodKey.equals(other.periodKey)) {
            return false;
        }
        if (periodType == null) {
            if (other.periodType != null) {
                return false;
            }
        } else if (!periodType.equals(other.periodType)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GoalPeriod [periodDesc=" + periodDesc + ", periodKey="
                + periodKey + ", periodType=" + periodType + "]";
    }

    @Override
    public int compareTo(GoalPeriod another) {
        int compareTo = periodKey.compareTo(another.periodKey);
        if (compareTo == 0) {
            return periodType.compareTo(another.periodType) * (-1);
        }
        return compareTo;
    }

}
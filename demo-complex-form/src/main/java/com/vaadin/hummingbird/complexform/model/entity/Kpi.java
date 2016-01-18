package com.vaadin.hummingbird.complexform.model.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.vaadin.hummingbird.complexform.model.helpers.ColumnMetaData;

/**
 * The persistent class for the KPI database table.
 *
 */
@SuppressWarnings("serial")
public class Kpi extends ScorecardEntity {

    private long id;

    private boolean active = true;

    private String dataSource;

    private String description;

    private String frequency = "Q";

    private String name;

    private String unitType = "N";

    private String polarity = "H";

    private Integer greenTolerance;

    private Integer yellowTolerance;

    private String rollupLogic = "S";

    private String initiatives;

    private String targetRationale;

    // bi-directional many-to-one association to Goal
    private Goal goal;

    // bi-directional many-to-one association to KpiAssignment
    // private Set<KpiAssignment> kpiAssignments;

    // bi-directional many-to-one association to KpiTarget
    private SortedSet<KpiTarget> kpiTargets;

    // bi-directional many-to-one association to KpiDetailsMetadata
    private Set<KpiDetailsMetadata> kpiDetailsMetadata;

    public Kpi() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInitiatives() {
        return initiatives;
    }

    public void setInitiatives(String initiatives) {
        this.initiatives = initiatives;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getPolarity() {
        return polarity;
    }

    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }

    public Integer getGreenTolerance() {
        return greenTolerance;
    }

    public void setGreenTolerance(Integer greenTolerance) {
        this.greenTolerance = greenTolerance;
    }

    public Integer getYellowTolerance() {
        return yellowTolerance;
    }

    public void setYellowTolerance(Integer yellowTolerance) {
        this.yellowTolerance = yellowTolerance;
    }

    public String getRollupLogic() {
        return rollupLogic;
    }

    public void setRollupLogic(String rollupLogic) {
        this.rollupLogic = rollupLogic;
    }

    public String getTargetRationale() {
        return targetRationale;
    }

    public void setTargetRationale(String targetRationale) {
        this.targetRationale = targetRationale;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    // public Set<KpiAssignment> getKpiAssignments() {
    // return kpiAssignments;
    // }
    //
    // public void setKpiAssignments(Set<KpiAssignment> kpiAssignments) {
    // this.kpiAssignments = kpiAssignments;
    // }
    //
    // public KpiAssignment addKpiAssignment(KpiAssignment kpiAssignment) {
    // getKpiAssignments().add(kpiAssignment);
    // kpiAssignment.setKpi(this);
    //
    // return kpiAssignment;
    // }
    //
    // public KpiAssignment removeKpiAssignment(KpiAssignment kpiAssignment) {
    // getKpiAssignments().remove(kpiAssignment);
    // kpiAssignment.setKpi(null);
    //
    // return kpiAssignment;
    // }

    public SortedSet<KpiTarget> getKpiTargets() {
        if (kpiTargets == null) {
            kpiTargets = new TreeSet<>();
        }
        return kpiTargets;
    }

    public void setKpiTargets(SortedSet<KpiTarget> kpiTargets) {
        this.kpiTargets = kpiTargets;
    }

    public KpiTarget addKpiTarget(KpiTarget kpiTarget) {
        getKpiTargets().add(kpiTarget);
        kpiTarget.setKpi(this);

        return kpiTarget;
    }

    public KpiTarget removeKpiTarget(KpiTarget kpiTarget) {
        getKpiTargets().remove(kpiTarget);
        kpiTarget.setKpi(null);

        return kpiTarget;
    }

    public Set<KpiDetailsMetadata> getKpiDetailsMetadata() {
        if (kpiDetailsMetadata == null) {
            kpiDetailsMetadata = new HashSet<>();
        }
        return kpiDetailsMetadata;
    }

    public void setKpiDetailsMetadata(
            Set<KpiDetailsMetadata> kpiDetailsMetadata) {
        this.kpiDetailsMetadata = kpiDetailsMetadata;
    }

    public KpiDetailsMetadata addKpiDetailsMetadata(
            KpiDetailsMetadata kpiDetailsMetadata) {
        getKpiDetailsMetadata().add(kpiDetailsMetadata);
        kpiDetailsMetadata.setKpi(this);

        return kpiDetailsMetadata;
    }

    public KpiDetailsMetadata removeKpiDetailsMetadata(
            KpiDetailsMetadata kpiDetailsMetadata) {
        getKpiDetailsMetadata().remove(kpiDetailsMetadata);
        kpiDetailsMetadata.setKpi(null);

        return kpiDetailsMetadata;
    }

    private List<ColumnMetaData> columnMetaData;

    public List<ColumnMetaData> getColumnMetaData() {
        if (columnMetaData == null) {
            columnMetaData = new ArrayList<>();
        }
        return columnMetaData;
    }

    public void setColumnMetaData(List<ColumnMetaData> columnMetaData) {
        this.columnMetaData = columnMetaData;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
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
        Kpi other = (Kpi) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}
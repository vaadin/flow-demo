package com.vaadin.hummingbird.complexform.model.entity;

import java.util.Date;

import com.vaadin.hummingbird.complexform.model.helpers.ScorecardUtil;

/**
 * The persistent class for the KPI_TARGETS database table.
 *
 */
@SuppressWarnings("serial")
public class KpiTarget extends ScorecardEntity
        implements Cloneable, Comparable<KpiTarget> {

    private Date periodKey;

    private long target = 0;

    KpiTargetId id = new KpiTargetId();

    private Kpi kpi;

    public String getPeriodDesc() {
        return id.periodDesc;
    }

    public void setPeriodDesc(String periodDesc) {
        id.periodDesc = periodDesc;
    }

    public Date getPeriodKey() {
        return periodKey;
    }

    public void setPeriodKey(Date periodKey) {
        this.periodKey = periodKey;
    }

    public long getTarget() {
        return target;
    }

    public void setTarget(long target) {
        this.target = target;
    }

    public Kpi getKpi() {
        return kpi;
    }

    public void setKpi(Kpi kpi) {
        this.kpi = kpi;
    }

    public String getPrettyPeriodDesc() {
        return ScorecardUtil.buildPrettyString(getPeriodKey(),
                kpi.getFrequency());
    }

    @Override
    public int compareTo(KpiTarget another) {
        return periodKey.compareTo(another.getPeriodKey());
    }

    @Override
    public String toString() {
        return "KpiTarget [periodKey=" + periodKey + ", target=" + target
                + ", id=" + id + ", kpi=" + kpi + "]";
    }

}
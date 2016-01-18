package com.vaadin.hummingbird.complexform.model.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class KpiDetailsMetadataId implements Serializable {

    private String columnType;
    private long kpiid;

    public KpiDetailsMetadataId() {
    }

    public KpiDetailsMetadataId(String columnType, long kpiid) {
        setColumnType(columnType);
        setKpiid(kpiid);
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public long getKpiid() {
        return kpiid;
    }

    public void setKpiid(long kpiid) {
        this.kpiid = kpiid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((getColumnType() == null) ? 0 : getColumnType().hashCode());
        result = prime * result + (int) (getKpiid() ^ (getKpiid() >>> 32));
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
        KpiDetailsMetadataId other = (KpiDetailsMetadataId) obj;
        if (getColumnType() == null) {
            if (other.getColumnType() != null) {
                return false;
            }
        } else if (!getColumnType().equals(other.getColumnType())) {
            return false;
        }
        if (getKpiid() != other.getKpiid()) {
            return false;
        }
        return true;
    }
}

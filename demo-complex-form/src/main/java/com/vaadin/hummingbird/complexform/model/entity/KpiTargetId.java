package com.vaadin.hummingbird.complexform.model.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class KpiTargetId implements Serializable {

    String periodDesc;
    long kpiid;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (kpiid ^ (kpiid >>> 32));
        result = prime * result
                + ((periodDesc == null) ? 0 : periodDesc.hashCode());
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
        KpiTargetId other = (KpiTargetId) obj;
        if (kpiid != other.kpiid) {
            return false;
        }
        if (periodDesc == null) {
            if (other.periodDesc != null) {
                return false;
            }
        } else if (!periodDesc.equals(other.periodDesc)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "KpiTargetId [periodDesc=" + periodDesc + ", kpiid=" + kpiid
                + "]";
    }

}
package com.vaadin.hummingbird.complexform.model.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class KpiId implements Serializable {

    long id;

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
        KpiId other = (KpiId) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
}

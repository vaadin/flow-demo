package com.vaadin.hummingbird.complexform.model.enums;

public enum Frequency {

    M, Q, A;

    public String toCaption() {
        if (Q.equals(this)) {
            return "Quarterly";
        } else if (M.equals(this)) {
            return "Monthly";
        } else if (A.equals(this)) {
            return "Annually";
        }
        return "Unnamed Frequency enum type";
    };
}

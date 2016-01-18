package com.vaadin.hummingbird.complexform.model.enums;

public enum UnitType {

    N, C, P;

    public String toCaption() {
        if (N.equals(this)) {
            return "Number";
        } else if (C.equals(this)) {
            return "Currency";
        } else if (P.equals(this)) {
            return "Percentages";
        }
        return "Unnamed UnitType enum type";
    }

}
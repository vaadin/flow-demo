package com.vaadin.hummingbird.complexform.model.entity;

import java.util.Arrays;
import java.util.List;

/**
 * The persistent class for the KPI_DETAILS_METADATA database table.
 *
 */
@SuppressWarnings("serial")
public class KpiDetailsMetadata extends ScorecardEntity implements Cloneable {

    private String column1Value;

    private String column2Value;

    private String column3Value;

    private String column4Value;

    private String column5Value;

    private String column6Value;

    private String column7Value;

    private String column8Value;

    private String column9Value;

    KpiDetailsMetadataId id = new KpiDetailsMetadataId();

    // bi-directional many-to-one association to Kpi
    private Kpi kpi;

    public KpiDetailsMetadata() {
    }

    public String getColumnType() {
        return id.getColumnType();
    }

    public void setColumnType(String columnType) {
        id.setColumnType(columnType);
    }

    public String getColumn1Value() {
        return column1Value;
    }

    public void setColumn1Value(String column1Value) {
        this.column1Value = column1Value;
    }

    public String getColumn2Value() {
        return column2Value;
    }

    public void setColumn2Value(String column2Value) {
        this.column2Value = column2Value;
    }

    public String getColumn3Value() {
        return column3Value;
    }

    public void setColumn3Value(String column3Value) {
        this.column3Value = column3Value;
    }

    public String getColumn4Value() {
        return column4Value;
    }

    public void setColumn4Value(String column4Value) {
        this.column4Value = column4Value;
    }

    public String getColumn5Value() {
        return column5Value;
    }

    public void setColumn5Value(String column5Value) {
        this.column5Value = column5Value;
    }

    public String getColumn6Value() {
        return column6Value;
    }

    public void setColumn6Value(String column6Value) {
        this.column6Value = column6Value;
    }

    public String getColumn7Value() {
        return column7Value;
    }

    public void setColumn7Value(String column7Value) {
        this.column7Value = column7Value;
    }

    public String getColumn8Value() {
        return column8Value;
    }

    public void setColumn8Value(String column8Value) {
        this.column8Value = column8Value;
    }

    public String getColumn9Value() {
        return column9Value;
    }

    public void setColumn9Value(String column9Value) {
        this.column9Value = column9Value;
    }

    public List<String> getValues() {
        return Arrays.asList(new String[] { column1Value, column2Value,
                column3Value, column4Value, column5Value, column6Value,
                column7Value, column8Value, column9Value });
    }

    public void setColumnValue(String value, int i) {
        switch (i) {
        case 1:
            column1Value = value;
            break;
        case 2:
            column2Value = value;
            break;
        case 3:
            column3Value = value;
            break;
        case 4:
            column4Value = value;
            break;
        case 5:
            column5Value = value;
            break;
        case 6:
            column6Value = value;
            break;
        case 7:
            column7Value = value;
            break;
        case 8:
            column8Value = value;
            break;
        case 9:
            column9Value = value;
            break;

        default:
            break;
        }
    }

    public String getColumnValue(int i) {
        String returnValue = null;
        switch (i) {
        case 1:
            returnValue = column1Value;
            break;
        case 2:
            returnValue = column2Value;
            break;
        case 3:
            returnValue = column3Value;
            break;
        case 4:
            returnValue = column4Value;
            break;
        case 5:
            returnValue = column5Value;
            break;
        case 6:
            returnValue = column6Value;
            break;
        case 7:
            returnValue = column7Value;
            break;
        case 8:
            returnValue = column8Value;
            break;
        case 9:
            returnValue = column9Value;
            break;
        default:
            break;
        }
        return returnValue;
    }

    public Kpi getKpi() {
        return kpi;
    }

    public void setKpi(Kpi kpi) {
        this.kpi = kpi;
    }

}
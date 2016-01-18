package com.vaadin.hummingbird.complexform.model.helpers;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ColumnMetaData implements Serializable {

    private String header;
    private String dataType;
    private int index;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
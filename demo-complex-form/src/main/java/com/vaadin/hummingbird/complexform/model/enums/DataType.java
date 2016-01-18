package com.vaadin.hummingbird.complexform.model.enums;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public enum DataType {

    N, C, P, T;
    public String toCaption() {
        if (N.equals(this)) {
            return "Number";
        } else if (C.equals(this)) {
            return "Currency";
        } else if (P.equals(this)) {
            return "Percentages";
        } else if (T.equals(this)) {
            return "Text";
        }
        return "Unnamed DataType enum type";
    }

    public String toShortCaption() {
        if (N.equals(this)) {
            return "#";
        } else if (C.equals(this)) {
            return "$";
        } else if (P.equals(this)) {
            return "%";
        } else if (T.equals(this)) {
            return "Aa";
        }
        return "Unnamed DataType enum type";
    }

    public static final DataTypeShortConverter SHORT_CONVERTER = new DataTypeShortConverter();

    @SuppressWarnings("serial")
    public static class DataTypeShortConverter
            implements Converter<String, String> {

        @Override
        public String convertToModel(String value,
                Class<? extends String> targetType, Locale locale)
                        throws com.vaadin.data.util.converter.Converter.ConversionException {
            return null; // NOOP
        }

        @Override
        public String convertToPresentation(String value,
                Class<? extends String> targetType, Locale locale)
                        throws com.vaadin.data.util.converter.Converter.ConversionException {
            return DataType.valueOf(value).toShortCaption();
        }

        @Override
        public Class<String> getModelType() {
            return String.class;
        }

        @Override
        public Class<String> getPresentationType() {
            return String.class;
        }

    }
}

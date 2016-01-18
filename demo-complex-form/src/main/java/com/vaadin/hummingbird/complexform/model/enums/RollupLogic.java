package com.vaadin.hummingbird.complexform.model.enums;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public enum RollupLogic {

    S, A;

    public String toCaption() {
        if (S.equals(this)) {
            return "Sum";
        } else if (A.equals(this)) {
            return "Average";
        }
        return "Unnamed RollupLogic enum type";
    }

    public static final RollupLogicConverter CONVERTER = new RollupLogicConverter();

    @SuppressWarnings("serial")
    public static class RollupLogicConverter
            implements Converter<String, String> {

        @Override
        public String convertToModel(String value,
                Class<? extends String> targetType, Locale locale)
                        throws com.vaadin.data.util.converter.Converter.ConversionException {
            return value.substring(0, 1).toUpperCase();
        }

        @Override
        public String convertToPresentation(String value,
                Class<? extends String> targetType, Locale locale)
                        throws com.vaadin.data.util.converter.Converter.ConversionException {
            return RollupLogic.valueOf(value).toCaption();
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

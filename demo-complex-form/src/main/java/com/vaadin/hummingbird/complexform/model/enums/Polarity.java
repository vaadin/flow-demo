package com.vaadin.hummingbird.complexform.model.enums;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public enum Polarity {

    L, H;

    public String toCaption() {
        if (equals(H)) {
            return "High";
        } else if (equals(L)) {
            return "Low";
        }
        return "Unnamed Polarity type";
    }

    public final static PolarityConverter CONVERTER = new PolarityConverter();

    @SuppressWarnings("serial")
    public static class PolarityConverter implements Converter<String, String> {

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
            return Polarity.valueOf(value).toCaption();
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

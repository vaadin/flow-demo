package com.vaadin.hummingbird.complexform.model.helpers;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.hummingbird.complexform.model.enums.DataType;

public class UnitTypeConverter implements Converter<String, String> {

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

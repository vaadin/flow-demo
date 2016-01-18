package com.vaadin.hummingbird.complexform.model.helpers;

import com.vaadin.data.validator.LongRangeValidator;

@SuppressWarnings("serial")
public class PercentageValidator extends LongRangeValidator {

    public PercentageValidator() {
        super("Please enter a valid percentage value, possible values are between -1000 and 1000, no decimals or letters.",
                -1000L, 1000L);
    }

    @Override
    public void validate(Object value) throws InvalidValueException {
        if (value instanceof String) {
            try {
                value = Long.parseLong((String) value);
            } catch (NumberFormatException nfe) {
                throw new InvalidValueException(getErrorMessage());
            }
        }
        super.validate(value);
    }
}

package com.vaadin.hummingbird.complexform.model.helpers;

import com.vaadin.data.validator.LongRangeValidator;

@SuppressWarnings("serial")
public class NumberValidator extends LongRangeValidator {

    public NumberValidator() {
        super("Please enter a valid number value between "
                + Long.toString(Long.MIN_VALUE) + "  and "
                + Long.toString(Long.MAX_VALUE) + ", without letters",
                Long.MIN_VALUE, Long.MAX_VALUE);
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

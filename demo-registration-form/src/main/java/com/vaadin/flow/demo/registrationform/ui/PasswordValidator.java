/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.demo.registrationform.ui;

import com.vaadin.data.ValidationResult;
import com.vaadin.data.ValueContext;
import com.vaadin.data.validator.StringLengthValidator;

/**
 * Custom password validator class.
 *
 * @author Vaadin Ltd
 *
 */
public class PasswordValidator extends StringLengthValidator {

    /**
     * Creates a new instance.
     */
    public PasswordValidator() {
        super("", 6, Integer.MAX_VALUE);
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        ValidationResult result = super.apply(value, context);
        if (result.isError()) {
            return ValidationResult
                    .error("Password should contain at least 6 characters");
        } else if (!hasDigit(value) || !hasLetter(value)) {
            return ValidationResult
                    .error("Password must contain a letter and a number");
        }
        return result;
    }

    private boolean hasDigit(String pwd) {
        return pwd.chars().anyMatch(Character::isDigit);
    }

    private boolean hasLetter(String pwd) {
        return pwd.chars().anyMatch(Character::isLetter);
    }
}

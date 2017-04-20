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
package com.vaadin.flow.demo.helloworld.element;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import com.vaadin.flow.html.Div;
import com.vaadin.flow.router.View;

/**
 * View containing text field and a div with text that is updated when selection
 * changes in the DateField
 */
public class DateView extends Div implements View {

    private Div text = new Div();

    /**
     * Initializes the view. Invoked by the framework when needed.
     */
    public DateView() {
        DateField dateField = new DateField();
        dateField.setId("date-field");

        dateField.addValueChangeListener(
                () -> updateTextContents(dateField.getValue()));
        text.setId("value");

        add(dateField, text);
    }

    private void updateTextContents(LocalDate value) {
        text.setText(value
                .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
    }
}

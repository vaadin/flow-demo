/*
 * Copyright 2000-2016 Vaadin Ltd.
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
package com.vaadin.hummingbird.demo.textfieldcomposite;

import java.util.function.Consumer;

import com.vaadin.hummingbird.demo.textfieldcomposite.Input.ChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Composite;

public class TextField extends Composite {

    private int id = 0;
    private VerticalLayout layout;
    private Label label;
    private Input input;

    @Override
    protected Component initContent() {
        return layout;
    }

    public TextField() {
        layout = new VerticalLayout();
        label = new Label();
        input = new Input();
        input.setId("input" + id++);
        label.setFor(input);
        layout.addComponents(label, input);
    }

    public TextField(String label) {
        this();
        setLabel(label);
    }

    public String getLabel() {
        return label.getText();
    }

    public void setLabel(String label) {
        this.label.setText(label);
    }

    public String getValue() {
        return input.getValue();
    }

    public void setValue(String value) {
        input.setValue(value);
    }

    public void addChangeListener(Consumer<ChangeEvent> changeListener) {
        // TODO Allow removal of listener
        input.addChangeListener(e -> {
            changeListener.accept(e);
        });
    }

}

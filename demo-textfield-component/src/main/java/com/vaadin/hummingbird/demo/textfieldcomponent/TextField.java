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
package com.vaadin.hummingbird.demo.textfieldcomponent;

import java.util.EventObject;
import java.util.function.Consumer;

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.ui.Component;

public class TextField extends Component {

    private int id = 0;
    private Element labelElement;
    private Element inputElement;

    public TextField() {
        super(ElementFactory.createDiv());
        labelElement = new Element("label");
        inputElement = new Element("input");

        String textFieldId = "textfield" + id++;
        inputElement.setAttribute("id", textFieldId);
        labelElement.setAttribute("for", textFieldId);

        inputElement.synchronizeProperty("value", "change");
        getElement().appendChild(labelElement, inputElement);
    }

    public TextField(String label) {
        this();
        setLabel(label);
    }

    public String getLabel() {
        return labelElement.getOwnTextContent();
    }

    public void setLabel(String label) {
        labelElement.setTextContent(label);
    }

    public String getValue() {
        return inputElement.getProperty("value", "");
    }

    public void setValue(String value) {
        inputElement.setProperty("value", value);
        // TODO Fire event
    }

    public static class ChangeEvent extends EventObject {
        private String newValue;

        public ChangeEvent(TextField source) {
            super(source);
            newValue = source.getValue();
        }

        public String getNewValue() {
            return newValue;
        }

    }

    public void addChangeListener(Consumer<ChangeEvent> changeListener) {
        // TODO Allow removal of listener
        inputElement.addEventListener("change", e -> {
            changeListener.accept(new ChangeEvent(this));
        });

    }

}

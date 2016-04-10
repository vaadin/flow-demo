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

import java.util.EventObject;
import java.util.function.Consumer;

import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.ui.Component;

public class Input extends Component {

    public Input() {
        super(ElementFactory.createInput());
        getElement().synchronizeProperty("input", "change");
    }

    public void setId(String id) {
        getElement().setAttribute("id", id);
    }

    public String getId() {
        return getElement().getAttribute("id");
    }

    public String getValue() {
        return getElement().getProperty("value");
    }

    public void setValue(String value) {
        getElement().setProperty("value", value);
    }

    public void addChangeListener(Consumer<ChangeEvent> listener) {
        getElement().addEventListener("change", e -> {
            listener.accept(new ChangeEvent(this));
        });
    }

    public static class ChangeEvent extends EventObject {
        public ChangeEvent(Input source) {
            super(source);
        }
    }

}

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
package com.vaadin.hummingbird.demo.webcomponent.bower;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.annotations.Id;
import com.vaadin.annotations.Uses;
import com.vaadin.hummingbird.demo.webcomponent.bower.component.ProgressBubble;
import com.vaadin.hummingbird.demo.webcomponent.bower.component.VaadinComboBox;
import com.vaadin.hummingbird.template.model.TemplateModel;
import com.vaadin.ui.Template;

/**
 * A template using web components.
 */
@Uses(ProgressBubble.class)
@Uses(VaadinComboBox.class)
public class RangeBubble extends Template {

    private static final String ELEMENT_VALUE = "element.value";

    public interface RangeBubbleModel extends TemplateModel {
        int getEnd();

        void setEnd(int end);

        int getValue();

        void setValue(int value);
    }

    @Id("rangeEnd")
    private VaadinComboBox rangeEnd;
    @Id("value")
    private VaadinComboBox value;
    @Id("bubble")
    private ProgressBubble bubble;

    /**
     * Creates the template instance.
     */
    public RangeBubble() {
        getModel().setValue(50);
        getModel().setEnd(80);

        List<String> values = new ArrayList<>();
        for (int i = 0; i < 100; i += 5) {
            values.add(String.valueOf(i));
        }
        rangeEnd.setItems(values);
        value.setItems(values);
        rangeEnd.setValue(Integer.toString(getModel().getEnd()));
        value.setValue(Integer.toString(getModel().getValue()));

        rangeEnd.getElement().addEventListener("value-changed",
                e -> getModel().setEnd(Integer
                        .parseInt(e.getEventData().getString(ELEMENT_VALUE))),
                ELEMENT_VALUE);

        value.getElement().addEventListener("value-changed",
                e -> getModel().setValue(Integer
                        .parseInt(e.getEventData().getString(ELEMENT_VALUE))),
                ELEMENT_VALUE);
    }

    @Override
    protected RangeBubbleModel getModel() {
        return (RangeBubbleModel) super.getModel();
    }
}

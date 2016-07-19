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
package com.vaadin.hummingbird.demo.webcomponent.polygit.component;

import java.util.List;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.demo.webcomponent.polygit.PolyGit;
import com.vaadin.hummingbird.util.JsonUtil;
import com.vaadin.ui.Component;

import elemental.json.impl.JreJsonString;

/**
 * An "integration" of vaadin-combo-box which provides no API, only the imports.
 */
@Tag("vaadin-combo-box")
@HtmlImport(PolyGit.BASE_URL + "vaadin-combo-box/vaadin-combo-box.html")
public class VaadinComboBox extends Component {

    /**
     * Sets the items to show in the combo box.
     *
     * @param values
     *            a list of strings to show in the combo box
     */
    public void setItems(List<String> values) {
        getElement().setPropertyJson("items", values.stream()
                .map(JreJsonString::new).collect(JsonUtil.asArray()));
    }

    /**
     * Sets the current value of the combo box.
     *
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        getElement().setProperty("value", value);

    }

}

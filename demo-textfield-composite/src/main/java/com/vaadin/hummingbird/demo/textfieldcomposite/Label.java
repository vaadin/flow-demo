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

import com.vaadin.hummingbird.dom.Element;
import com.vaadin.ui.Component;

public class Label extends Component {

    public Label() {
        super(new Element("label"));
    }

    public void setFor(Component c) {
        setFor(c.getElement());
    }

    public void setFor(Element e) {
        if (!e.hasAttribute("id")) {
            throw new IllegalArgumentException(
                    "The given element must have an id");
        }
        getElement().setAttribute("for", e.getAttribute("id"));
    }

    public String getFor() {
        return getElement().getAttribute("for");
    }

    public String getText() {
        return getElement().getOwnTextContent();
    }

    public void setText(String text) {
        getElement().setTextContent(text);
    }
}

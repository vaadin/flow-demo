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
package com.vaadin.hummingbird.demo.staticmenu;

import com.vaadin.hummingbird.dom.ElementFactory;

/**
 * An abstract view which automatically contains a text, which tells the name of
 * the view.
 *
 * @author Vaadin
 * @since
 */
public abstract class DummyView extends SimpleView {

    /**
     * Creates the view.
     */
    public DummyView() {
        super(ElementFactory.createDiv());
        getElement().setAttribute("class", "content");

        getElement().setTextContent(
                "This is the " + Util.getViewName(getClass()) + " view");
    }

}

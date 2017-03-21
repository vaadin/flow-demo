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
package com.vaadin.hummingbird.demo.jquerytable.element.html5;

import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.html.HtmlContainer;
import com.vaadin.hummingbird.html.event.ClickNotifier;
import com.vaadin.ui.Component;

/**
 * Component representing a <code>&lt;main&gt;</code> element.
 */
@Tag("main")
public class Main extends HtmlContainer implements ClickNotifier {

    /**
     * Creates a new empty main.
     */
    public Main() {
        super();
    }

    /**
     * Creates a new main with the given child components.
     *
     * @param components
     *            the child components
     */
    public Main(Component... components) {
        super(components);
    }

}
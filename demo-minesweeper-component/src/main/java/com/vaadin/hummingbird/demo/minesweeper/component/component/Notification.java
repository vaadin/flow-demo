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
package com.vaadin.hummingbird.demo.minesweeper.component.component;

import com.vaadin.hummingbird.html.Div;

/**
 * A really simple notification component, which is just a {@link Div} with a
 * class name.
 *
 * @author Vaadin Ltd
 * @since
 */
public class Notification extends Div {

    /**
     * Creates a notification with the given text and class name.
     *
     * @param text
     *            the text to show
     * @param className
     *            the class name to use
     */
    public Notification(String text, String className) {
        setText(text);
        addClassName(className);
    }

}

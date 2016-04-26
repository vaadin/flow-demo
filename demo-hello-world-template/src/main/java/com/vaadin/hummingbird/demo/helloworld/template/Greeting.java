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
package com.vaadin.hummingbird.demo.helloworld.template;

import com.vaadin.ui.Template;

/**
 * A greeting component which uses template data binding to show a text.
 *
 * @author Vaadin Ltd
 * @since
 */
public class Greeting extends Template {

    /**
     * Sets the text which is shown in the template.
     *
     * @param text
     *            the text to show
     */
    public void setText(String text) {
        setModelValue("text", text);
    }

}

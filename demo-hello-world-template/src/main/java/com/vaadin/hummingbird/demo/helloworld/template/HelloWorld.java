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

import com.vaadin.annotations.EventHandler;
import com.vaadin.hummingbird.html.Input;
import com.vaadin.hummingbird.nodefeature.ModelMap;
import com.vaadin.hummingbird.nodefeature.TemplateMap;
import com.vaadin.ui.Template;

/**
 * The one and only view in the hello world application.
 */
public class HelloWorld extends Template {

    private Input input = new Input();

    /**
     * Initializes the view. Invoked by the framework when needed.
     */
    public HelloWorld() {
        input.setId("inputId");
        injectInput();
    }

    private void injectInput() {
        // This is a hack until there is support for parameter passing to
        // @TemplateEventHandler
        TemplateMap templateMap = getElement().getNode()
                .getFeature(TemplateMap.class);
        templateMap.setChild(input.getElement().getNode());

    }

    @EventHandler
    private void sayHello() {
        // Called from the template click handler
        String inputValue = input.getValue();
        String text;
        if (inputValue == null || inputValue.isEmpty()) {
            text = "Don't be shy";
        } else {
            text = "Hello " + inputValue + "!";
        }

        // This is a hack until there is proper model support in template
        getElement().getNode().getFeature(ModelMap.class).setValue("text",
                text);
    }
}

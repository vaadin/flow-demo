/*
 * Copyright 2000-2017 Vaadin Ltd.
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
package com.vaadin.flow.demo.helloworld.template;

import java.util.Optional;

import com.vaadin.annotations.EventHandler;
import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Tag;
import com.vaadin.flow.template.PolymerTemplate;
import com.vaadin.flow.template.model.TemplateModel;

/**
 * The one and only view in the hello world application.
 */
@Tag("hello-world")
@HtmlImport("components/HelloWorld.html")
public class HelloWorld extends PolymerTemplate<HelloWorld.HelloWorldModel> {
    private static final String EMPTY_NAME_GREETING = "Please enter your name";

    /**
     * Creates the hello world template.
     */
    public HelloWorld() {
        setId("template");
        getModel().setGreeting(EMPTY_NAME_GREETING);
    }

    /**
     * Model for the template.
     */
    public interface HelloWorldModel extends TemplateModel {
        /**
         * Gets user input from corresponding template page.
         *
         * @return user input string
         */
        String getUserInput();

        /**
         * Sets greeting that is displayed in corresponding template page.
         *
         * @param greeting
         *            greeting string
         */
        void setGreeting(String greeting);
    }

    @EventHandler
    private void sayHello() {
        // Called from the template click handler
        getModel().setGreeting(Optional.ofNullable(getModel().getUserInput())
                .filter(userInput -> !userInput.isEmpty())
                .map(greeting -> String.format("Hello %s!", greeting))
                .orElse(EMPTY_NAME_GREETING));
    }
}

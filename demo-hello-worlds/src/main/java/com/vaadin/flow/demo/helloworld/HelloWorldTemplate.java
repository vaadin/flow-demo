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
package com.vaadin.flow.demo.helloworld;

import com.vaadin.flow.demo.helloworld.HelloWorldTemplate.HelloWorldModel;
import com.vaadin.flow.model.TemplateModel;
import com.vaadin.router.Route;
import com.vaadin.router.Title;
import com.vaadin.ui.Tag;
import com.vaadin.ui.common.HtmlImport;
import com.vaadin.ui.polymertemplate.PolymerTemplate;

/**
 * Hello World view based on templates. The composition of elements is done
 * based on the contents of the HelloWorld.html file. Data is communicated
 * between the client and the server based on the HelloWorldModel.
 */
@Tag("hello-world")
@HtmlImport("frontend://src/HelloWorld.html")
@Title("Hello World with a template")
@Route(value = "template", layout = MainLayout.class)
public class HelloWorldTemplate extends PolymerTemplate<HelloWorldModel> {

    /**
     * Template constructor.
     */
    public HelloWorldTemplate() {
        // Set the initial greeting value
        updateGreeting();

        // Listen for value change events. The name property is updated by logic
        // in the template.
        getElement().addPropertyChangeListener("name",
                event -> updateGreeting());

        // Set up a DOM id value used for integration tests
        setId("template");
    }

    private void updateGreeting() {
        String name = getModel().getName();

        // Update the model based on the name
        if (name == null || name.isEmpty()) {
            getModel().setGreeting("Please enter your name");
        } else {
            getModel().setGreeting(String.format("Hello %s!", name));
        }
    }

    /**
     * A model interface that defined the data that is communicated between the
     * server and the client.
     */
    public interface HelloWorldModel extends TemplateModel {
        /**
         * The name shown in the input is updated from the client.
         */
        String getName();

        /**
         * The greeting is updated from Java code on the server.
         */
        void setGreeting(String greeting);
    }
}

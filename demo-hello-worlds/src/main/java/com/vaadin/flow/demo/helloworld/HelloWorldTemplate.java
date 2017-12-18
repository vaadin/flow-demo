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

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.demo.helloworld.HelloWorldTemplate.HelloWorldModel;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * Hello World view based on templates. The composition of elements is done
 * based on the contents of the HelloWorld.html file. Data is communicated
 * between the client and the server based on the HelloWorldModel.
 */
@Tag("hello-world")
@HtmlImport("frontend://src/HelloWorld.html")
@PageTitle("Hello World with a template")
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
         * 
         * @return current name in model
         */
        String getName();

        /**
         * The greeting is updated from Java code on the server.
         * 
         * @param greeting
         *            greeting to set to the model
         */
        void setGreeting(String greeting);
    }
}

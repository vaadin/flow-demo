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
package com.vaadin.hummingbird.demo.helloworld.element;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.hummingbird.dom.Element;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * UI which demonstrates how a simple Hello World type application can be
 * created.
 */
public class HelloWorldUI extends UI {

    /**
     * The main servlet for the application.
     */
    @WebServlet(urlPatterns = "/*", name = "UIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = HelloWorldUI.class, productionMode = false)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        Element textInput = new Element("input").setAttribute("id", "inputId")
                .setAttribute("placeholder", "Enter your name")
                .setSynchronizedProperties("value")
                .setSynchronizedPropertiesEvents("change");

        Element button = new Element("button").setTextContent("Say hello");
        button.addEventListener("click", e -> {
            Element helloText = new Element("div")
                    .setTextContent("Hello " + textInput.getProperty("value"));
            helloText.getClassList().add("helloText");
            getElement().appendChild(helloText);
        });

        getElement().appendChild(textInput, button);
    }

}

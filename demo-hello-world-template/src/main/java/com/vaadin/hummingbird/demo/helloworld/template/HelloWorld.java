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

import com.vaadin.hummingbird.html.Button;
import com.vaadin.hummingbird.html.Div;
import com.vaadin.hummingbird.html.Input;
import com.vaadin.hummingbird.router.View;

/**
 * The one and only view in the hello world application.
 */
public class HelloWorld extends Div implements View {

    Greeting greeting;

    /**
     * Initializes the view. Invoked by the framework when needed.
     */
    public HelloWorld() {
        Input textInput = new Input();
        textInput.setId("inputId");
        textInput.setPlaceholder("Enter your name");

        Button button = new Button("Say hello");
        button.addClickListener(e -> {
            greeting.setText("Hello " + textInput.getValue() + "!");
        });

        greeting = new Greeting();
        add(textInput, button, greeting);
    }
}

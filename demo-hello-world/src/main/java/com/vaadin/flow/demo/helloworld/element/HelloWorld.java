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
package com.vaadin.flow.demo.helloworld.element;

import com.vaadin.flow.html.NativeButton;
import com.vaadin.flow.html.Div;
import com.vaadin.flow.html.Input;
import com.vaadin.flow.router.View;

/**
 * The one and only view in the hello world application.
 */
public class HelloWorld extends Div implements View {

    /**
     * Initializes the view. Invoked by the framework when needed.
     */
    public HelloWorld() {
        Input textInput = new Input();
        textInput.setId("inputId");
        textInput.setPlaceholder("Enter your name");

        NativeButton button = new NativeButton("Say hello");
        button.addClickListener(e -> {
            Div hello = new Div();
            hello.setText("Hello " + textInput.getValue());
            hello.setClassName("helloText");
            add(hello);
        });

        add(textInput, button);
    }
}

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
package com.vaadin.flow.demo.textfieldcomposite;

import javax.servlet.annotation.WebServlet;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletConfiguration;
import com.vaadin.ui.UI;
import com.vaadin.ui.html.Div;

/**
 * UI which demonstrates how a text field component can be used.
 */
public class TextFieldUI extends UI {

    /**
     * The main servlet for the application.
     */
    @WebServlet(urlPatterns = "/*", name = "UIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = TextFieldUI.class, productionMode = false)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        TextField tf = new TextField("Enter your age");
        tf.addChangeListener(event -> {
            int age;
            try {
                age = Integer.parseInt(tf.getValue());
            } catch (NumberFormatException e) {
                age = -1;
            }

            Div message = new Div();
            message.setText(getAgeMessage(age));
            add(message);
        });
        add(tf);
    }

    private static String getAgeMessage(int age) {
        if (age < 0) {
            return "That's not even a real age!";
        } else if (age > 18) {
            return "Gosh, " + age + " is so old!";
        } else {
            return "Oh my, " + age + " is so young!";
        }
    }

}

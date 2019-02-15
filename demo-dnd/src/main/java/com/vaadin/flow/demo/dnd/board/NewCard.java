/*
 * Copyright 2000-2019 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.vaadin.flow.demo.dnd.board;

import java.util.function.Consumer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.demo.dnd.model.Card;

public class NewCard extends Div {

    private final TextField nameField;

    public NewCard(Consumer<Card> newCardConsumer) {
        addClassName("new-card");
        addClassName("card");
        nameField = new TextField();
        nameField.setPlaceholder("Name");
        TextArea descriptionField = new TextArea();
        descriptionField.setPlaceholder("Description");
        Button createButton = new Button("Create new", event -> {
            newCardConsumer.accept(
                    new Card(nameField.getValue(), descriptionField.getValue(),
                            Card.Status.INBOX));
            nameField.clear();
            descriptionField.clear();
        });
        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(nameField, descriptionField, createButton);
    }
}

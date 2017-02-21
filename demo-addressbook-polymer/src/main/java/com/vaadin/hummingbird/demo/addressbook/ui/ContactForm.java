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
package com.vaadin.hummingbird.demo.addressbook.ui;

import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.demo.addressbook.backend.Contact;
import com.vaadin.hummingbird.demo.addressbook.backend.ContactService;
import com.vaadin.hummingbird.html.HtmlComponent;
import elemental.json.Json;
import elemental.json.JsonObject;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Contact editor form.
 *
 * @author Vaadin Ltd
 *
 */
@Tag("contacts-form")
class ContactForm extends HtmlComponent {
    private final Consumer<Boolean> elementUpdateCallback;

    private JsonObject selectedObject = Json.createObject();

    ContactForm(Consumer<Boolean> elementUpdateCallback) {
        this.elementUpdateCallback = elementUpdateCallback;

        getElement().addEventListener("form-saved",
                e -> updateContact(e.getEventData()), "event.detail");

        getElement().addEventListener("form-closed",
                e -> elementUpdateCallback.accept(false));
    }

    void updateContent(JsonObject selectedObject) {
        this.selectedObject = selectedObject;
        getElement().setPropertyJson("contactToEdit", selectedObject);
    }

    private void updateContact(JsonObject eventData) {
        JsonObject newContact = eventData.get("event.detail");
        if (!Objects.equals(selectedObject, newContact)) {
            ContactService.getDemoService().save(new Contact(newContact));
            elementUpdateCallback.accept(true);
            selectedObject = newContact;
        }
    }
}
